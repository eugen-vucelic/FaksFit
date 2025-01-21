package com.app.faksfit.service.impl;

import com.app.faksfit.model.Teacher;
import com.app.faksfit.repository.TeacherRepository;
import com.app.faksfit.service.ITeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findByUserId(id);
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }
}
