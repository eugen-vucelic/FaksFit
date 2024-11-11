package com.app.faksfit.controller;

import com.app.faksfit.model.Term;
import com.app.faksfit.service.impl.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TermController {

    @Autowired
    private TermService termService;

    @GetMapping("/svi-termini")
    public List<Term> getTerms() {
        return termService.getAllTerms();
    }
}
