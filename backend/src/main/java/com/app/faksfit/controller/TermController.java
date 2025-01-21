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
    public ResponseEntity<List<TermDTO>> getTerms() {
        List<Term> terminList = termService.getAllTerms();

        if (terminList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TermDTO> terms = termMapper.toTermDTOList(terminList);

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @GetMapping("/dostupni-termini")
    public ResponseEntity<List<TermDTO>> getAvailableTerms(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.status(404).body(null);
        }

        List<Term> terms = termSignUpService.getAvailableTerms(student);

        if (terms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<TermDTO> termDTOlist = termMapper.toTermDTOList(terms);

        return new ResponseEntity<>(termDTOlist, HttpStatus.OK);
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
}
