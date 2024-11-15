package com.app.faksfit.repository;

import com.app.faksfit.model.Faculty;
import com.app.faksfit.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Teacher findByEmail(String email);

    @Query("SELECT t FROM Teacher t WHERE t.firstName = :firstName AND t.lastName = :lastName")
    List<Teacher> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

    List<Teacher> findByUserFaculty(Faculty faculty);

    @Query("SELECT t FROM Teacher t WHERE SIZE(t.students) > :studentCount")
    List<Teacher> findTeachersWithMoreThanNStudents(@Param("studentCount") int studentCount);

    @Query("SELECT t FROM Teacher t JOIN t.students s WHERE s.userId = :studentId")
    Teacher findByStudentId(@Param("studentId") Long studentId);

    long countByUserFaculty(Faculty faculty);

    @Query("SELECT t FROM Teacher t WHERE SIZE(t.students) = 0")
    List<Teacher> findTeachersWithoutStudents();

    @Query(value = "SELECT t FROM Teacher t WHERE t.userFaculty = :faculty ORDER BY FUNCTION('RAND')")
    Teacher findRandomTeacherByFaculty(@Param("faculty") Faculty faculty);

}
