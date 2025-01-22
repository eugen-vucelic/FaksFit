package com.app.faksfit.service.impl;

import com.app.faksfit.dto.StudentTeacherDTO;
import com.app.faksfit.mapper.StudentTeacherMapper;
import com.app.faksfit.model.Teacher;
import com.app.faksfit.repository.StudentRepository;
import com.app.faksfit.repository.TeacherRepository;
import com.app.faksfit.service.ITeacherService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherRepository teacherRepository;

    private final StudentRepository studentRepository;

    private final StudentTeacherMapper studentTeacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, StudentRepository studentRepository, StudentTeacherMapper studentTeacherMapper) {
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.studentTeacherMapper = studentTeacherMapper;
    }

    @Override
    public Teacher getById(Long id) {
        return teacherRepository.findByUserId(id);
    }

    @Override
    public Teacher findByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public List<StudentTeacherDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(studentTeacherMapper::toStudentTeacherDTO)
                .collect(Collectors.toList());
    }
}
