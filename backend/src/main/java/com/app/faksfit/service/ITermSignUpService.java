package com.app.faksfit.service;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.Term;

import java.util.List;

public interface ITermSignUpService {
    List<Term> getAvailableTerms(Student student);

    void addUserToTerm(Long termId, Student student);
}
