package com.app.faksfit.repository;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByJMBAG(String JMBAG);

    Student findByUserId(Long userId);

    List<Student> findByPassStatus(Boolean passStatus);

    List<Student> findBySemester(String semester);

    List<Student> findByTotalPointsGreaterThan(Integer points);

    List<Student> findByTotalPointsBetween(Integer minPoints, Integer maxPoints);

    List<Student> findByStudentTeacher(Teacher teacher);

    @Query("SELECT s FROM Student s JOIN s.terminList t WHERE t.term.termId = :termId")
    List<Student> findStudentsByTermId(@Param("termId") Long termId);

    boolean existsByJMBAG(String JMBAG);

    List<Student> findByAcademicYearAndTotalPointsGreaterThan(String academicYear, Integer points);

    Student findByEmail(String email);
}
