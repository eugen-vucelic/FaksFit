package com.app.faksfit.service;


import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.model.Student;

public interface IStudentService {

    public Student getByJMBAG(String jmbag);

    public void addStudent(StudentDTO studentDTO);
}
