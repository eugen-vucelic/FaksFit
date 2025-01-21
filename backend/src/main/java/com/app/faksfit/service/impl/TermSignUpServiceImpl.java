package com.app.faksfit.service.impl;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import com.app.faksfit.model.Term;
import com.app.faksfit.repository.StudentRepository;
import com.app.faksfit.repository.StudentTerminAssocRepository;
import com.app.faksfit.repository.TermRepository;
import com.app.faksfit.service.ITermSignUpService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TermSignUpServiceImpl implements ITermSignUpService {
    private final TermRepository termRepository;
    private final StudentRepository studentRepository;
    private final StudentTerminAssocRepository studentTerminAssocRepository;

    @Autowired
    public TermSignUpServiceImpl(TermRepository termRepository, StudentRepository studentRepository, StudentTerminAssocRepository studentTerminAssocRepository) {
        this.termRepository = termRepository;
        this.studentRepository = studentRepository;
        this.studentTerminAssocRepository = studentTerminAssocRepository;
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
    @Transactional
    public void addUserToTerm(Long termId, Student student) {
        if (termId == null || student == null) {
            throw new IllegalArgumentException("TermId and student must not be null");
        }

        Term term = termRepository.findByTermId(termId);
        if (term == null) {
            throw new IllegalArgumentException("Termin s tim id-om ne postoji!");
        }

        List<Term> fullyOccupiedTerms = termRepository.findFullTerms();
        if (fullyOccupiedTerms.contains(term)) {
            throw new IllegalStateException("Termin je popunjen!");
        }

        try {
            StudentTerminAssoc assoc = new StudentTerminAssoc();
            assoc.setStudent(student);
            assoc.setTerm(term);
            assoc.setPointsAchieved(0);

            studentTerminAssocRepository.save(assoc);
            student.getTerminList().add(assoc);
            studentRepository.save(student);
            term.getStudentList().add(assoc);
            termRepository.save(term);

        } catch (Exception e) {
            throw new RuntimeException("Greška pri spremanju podataka", e);
        }
    }
}
