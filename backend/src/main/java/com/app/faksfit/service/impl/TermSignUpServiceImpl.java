package com.app.faksfit.service.impl;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import com.app.faksfit.model.Term;
import com.app.faksfit.repository.StudentRepository;
import com.app.faksfit.repository.TermRepository;
import com.app.faksfit.service.ITermSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TermSignUpServiceImpl implements ITermSignUpService {
    private final TermRepository termRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public TermSignUpServiceImpl(TermRepository termRepository, StudentRepository studentRepository) {
        this.termRepository = termRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<Term> getAvailableTerms(Student student) {
        List<Term> availableTerms = termRepository.findTermsWithAvailableCapacity();

        List<StudentTerminAssoc> signedUpTerminAssocs = student.getTerminList();
        List<Term> signedUpTerms = signedUpTerminAssocs.stream()
                .map(StudentTerminAssoc::getTerm)
                .toList();

        return availableTerms.stream()
                .filter(term -> term.getTermStart().isAfter(LocalDateTime.now()))
                .filter(term -> !signedUpTerms.contains(term))
                .toList();
    }

    @Override
    public void addUserToTerm(Long termId, Student student) {
        Term term = termRepository.findById(termId)
                .orElseThrow(() -> new IllegalArgumentException("Termin s identifikatorom " + termId + " nije pronađen!"));

        List<Term> fullyOccupiedTerms = termRepository.findFullTerms();
        if (fullyOccupiedTerms.contains(term)) {
            throw new IllegalStateException("Termin je popunjen!");
        }

        StudentTerminAssoc assoc = new StudentTerminAssoc(student, term, 0);
        student.getTerminList().add(assoc);

        studentRepository.save(student);
    }
}
