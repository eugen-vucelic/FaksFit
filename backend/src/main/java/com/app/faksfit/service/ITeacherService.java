package com.app.faksfit.service;

import com.app.faksfit.dto.StudentTeacherDTO;
import com.app.faksfit.model.Teacher;

import java.util.List;

public interface ITeacherService {

    Teacher getById(Long id);

    Teacher findByEmail(String email);

    List<StudentTeacherDTO> getAllStudents();

}
