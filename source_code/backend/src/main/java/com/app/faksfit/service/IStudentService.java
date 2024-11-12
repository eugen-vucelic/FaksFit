package com.app.faksfit.service;


import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentDTONoEmail;
import com.app.faksfit.model.Student;

public interface IStudentService {

    Student getByJMBAG(String jmbag);

    Student getById(Long id);

    void addStudent(StudentDTONoEmail studentDTO, String email);

    Student findByEmail(String email);
}
