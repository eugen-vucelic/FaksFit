package com.app.faksfit.service;

import com.app.faksfit.model.Teacher;

import java.util.List;

public interface ITeacherService {

    Teacher getById(Long id);

    Teacher findByEmail(String email);

}
