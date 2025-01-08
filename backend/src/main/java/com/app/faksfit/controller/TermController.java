package com.app.faksfit.controller;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.Term;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.service.impl.TermService;
import com.app.faksfit.service.impl.TermSignUpServiceImpl;
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

    public TermController(TermService termService , TermSignUpServiceImpl termSignUpService, StudentServiceImpl studentService) {
        this.termService = termService;
        this.termSignUpService = termSignUpService;
        this.studentService = studentService;
    }

    @GetMapping("/svi-termini")
    public ResponseEntity<List<Term>> getTerms() {
        List<Term> terms = termService.getAllTerms();

        if (terms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }

    @GetMapping("/dostupni-termini/")
    public ResponseEntity<List<Term>> getAvailableTerms(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.status(404).body(null);
        }

        List<Term> terms = termSignUpService.getAvailableTerms(student);

        if (terms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
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
