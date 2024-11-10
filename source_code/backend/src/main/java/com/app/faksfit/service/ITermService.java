package com.app.faksfit.service;

import com.app.faksfit.model.Term;

import java.time.LocalDateTime;
import java.util.List;

public interface ITermService {
    public List<Term> getAllTerms();

    public List<Term> getTermsAfterNow();
}
