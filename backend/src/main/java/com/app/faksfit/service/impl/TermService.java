package com.app.faksfit.service.impl;

import com.app.faksfit.model.Term;
import com.app.faksfit.repository.TermRepository;
import com.app.faksfit.service.ITermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TermService implements ITermService {
    private final TermRepository termRepository;

    @Autowired
    public TermService(TermRepository termRepository) {
        this.termRepository = termRepository;
    }

    @Override
    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }

    @Override
    public List<Term> getTermsAfterNow() {
        return termRepository.findByTermStartAfter(LocalDateTime.now());
    }
}
