package com.app.faksfit.controller;

import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.mapper.TermMapper;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.Term;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.service.impl.TermService;
import com.app.faksfit.service.impl.TermSignUpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/termini")
public class TermController {

    private final TermService termService;
    private final TermSignUpServiceImpl termSignUpService;
    private final StudentServiceImpl studentService;
    private final TermMapper termMapper;

    @Autowired
    public TermController(TermService termService , TermSignUpServiceImpl termSignUpService, StudentServiceImpl studentService, TermMapper termMapper) {
        this.termService = termService;
        this.termSignUpService = termSignUpService;
        this.studentService = studentService;
        this.termMapper = termMapper;
    }

    @GetMapping("/svi-termini")
    public ResponseEntity<List<TermDTO>> getTerms(@AuthenticationPrincipal OAuth2User oauthUser) {
        if (oauthUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = oauthUser.getAttribute("email");
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Term> availableTerms = termSignUpService.getAvailableTerms(student);

        if (availableTerms.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        List<TermDTO> termDTOList = termMapper.toTermDTOList(availableTerms);
        return ResponseEntity.ok(termDTOList);
    }

    @PostMapping("/upis-na-termin/{termId}")
    public ResponseEntity<String> addUserTerm(@PathVariable Long termId, @AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.status(404).body(null);
        }

        try {
            termSignUpService.addUserToTerm(termId, student);
            return new ResponseEntity<>("User successfully added to the term", HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/odjava/{termId}")
    public ResponseEntity<String> removeUserFromTerm(
            @PathVariable Long termId,
            @AuthenticationPrincipal OAuth2User oauthUser) {

        String email = oauthUser.getAttribute("email");
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.ok("Student not found");
        }

        try {
            termSignUpService.removeUserFromTerm(termId, student);
            return ResponseEntity.ok("User successfully removed from the term");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
