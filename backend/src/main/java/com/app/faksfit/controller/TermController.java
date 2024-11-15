package com.app.faksfit.controller;

import com.app.faksfit.model.Term;
import com.app.faksfit.service.impl.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/termini")
public class TermController {

    private final TermService termService;

    public TermController(TermService termService) {
        this.termService = termService;
    }

    @GetMapping("/svi-termini")
    public ResponseEntity<List<Term>> getTerms() {
        List<Term> terms = termService.getAllTerms();

        if (terms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(terms, HttpStatus.OK);
    }
}
