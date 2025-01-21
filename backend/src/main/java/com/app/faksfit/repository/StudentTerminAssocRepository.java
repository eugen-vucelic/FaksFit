package com.app.faksfit.repository;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import com.app.faksfit.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTerminAssocRepository extends JpaRepository<StudentTerminAssoc, Long> {
    List<StudentTerminAssoc> findByStudent(Student student);
    List<StudentTerminAssoc> findByTerm(Term term);
    StudentTerminAssoc findByStudentAndTerm(Student student, Term term);
    void deleteByStudentAndTerm(Student student, Term term);
    void deleteByStudent(Student student);
    void deleteByTerm(Term term);
}
