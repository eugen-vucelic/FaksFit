package com.app.faksfit.mapper;

import com.app.faksfit.dto.StudentTeacherDTO;
import com.app.faksfit.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentTeacherMapper {

    public StudentTeacherDTO toStudentTeacherDTO(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student must not be null");
        }

        return new StudentTeacherDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getEmail(),
                student.getJMBAG(),
                student.getSemester(),
                student.getTotalPoints()
        );
    }
}
