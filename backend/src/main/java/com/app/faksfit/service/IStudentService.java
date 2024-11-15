package com.app.faksfit.service;


import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentSettingsDTO;
import com.app.faksfit.model.Student;

import java.util.List;

public interface IStudentService {

    Student getByJMBAG(String jmbag);

    Student getById(Long id);

    void addStudent(StudentDTO studentDTO);

    Student findByEmail(String email);

    List<Student> getAllStudents();

    boolean updateStudent(String email, StudentSettingsDTO studentSettingsDTO);
}
