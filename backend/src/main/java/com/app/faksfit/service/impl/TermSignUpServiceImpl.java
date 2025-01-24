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
import java.util.Set;
import java.util.stream.Collectors;

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
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        List<Term> availableTerms = termRepository.findTermsWithAvailableCapacity();
        Set<Long> signedUpTermIds = student.getTerminList().stream()
                .map(assoc -> assoc.getTerm().getTermId())
                .collect(Collectors.toSet());

        return availableTerms.stream()
                .filter(term -> !signedUpTermIds.contains(term.getTermId()))
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

        if (term.getStudentList().size() >= term.getCapacity()) {
            throw new IllegalStateException("Termin je popunjen!");
        }

        try {
            StudentTerminAssoc assoc = new StudentTerminAssoc();
            assoc.setStudent(student);
            assoc.setTerm(term);
            assoc.setPointsAchieved(0);

            studentTerminAssocRepository.save(assoc);
            student.getTerminList().add(assoc);
            term.getStudentList().add(assoc);

            studentRepository.save(student);
            termRepository.save(term);

        } catch (Exception e) {
            throw new RuntimeException("Greška pri spremanju podataka", e);
        }
    }

    @Override
    public void removeUserFromTerm(Long termId, Student student) {
        if (termId == null || student == null) {
            throw new IllegalArgumentException("TermId and student must not be null");
        }

        Term term = termRepository.findByTermId(termId);
        if (term == null) {
            throw new IllegalArgumentException("Termin s tim id-om ne postoji!");
        }

        StudentTerminAssoc assoc = studentTerminAssocRepository.findByStudentAndTerm(student, term);
        if (assoc == null) {
            throw new IllegalArgumentException("Student nije prijavljen na taj termin!");
        }

        try {
            student.getTerminList().remove(assoc);
            term.getStudentList().remove(assoc);

            studentRepository.save(student);
            termRepository.save(term);

            studentTerminAssocRepository.delete(assoc);

        } catch (DataAccessException e) {
            throw new RuntimeException("Greška pri spremanju podataka", e);
        }
    }
}
