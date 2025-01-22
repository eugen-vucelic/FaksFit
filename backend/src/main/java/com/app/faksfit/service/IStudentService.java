package com.app.faksfit.service;


import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentSettingsDTO;
import com.app.faksfit.model.Student;

public interface IStudentService extends IUserService {

    Student getByJMBAG(String jmbag);

    @Override
    Student getById(Long id);

    void addStudent(StudentDTO studentDTO);

    @Override
    Student findByEmail(String email);

    boolean updateStudent(String email, StudentSettingsDTO studentSettingsDTO);
}
