package com.app.faksfit.service.impl;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentDashboardDTO;
import com.app.faksfit.mapper.StudentDashboardMapper;
import com.app.faksfit.mapper.StudentMapper;
import com.app.faksfit.model.Student;
import com.app.faksfit.repository.StudentRepository;
import com.app.faksfit.service.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements IStudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final StudentDashboardMapper studentDashboardMapper;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, StudentDashboardMapper studentDashboardMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.studentDashboardMapper = studentDashboardMapper;
    }

    @Override
    public Student getByJMBAG(String jmbag) {
        return studentRepository.findByJMBAG(jmbag);
    }

    @Override
    public Student getById(Long id) {
        return studentRepository.findByUserId(id);
    }

    @Override
    public void addStudent(StudentDTO studentDTO) {
        studentRepository.save(studentMapper.toEntity(studentDTO));
    }

    @Override
    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}
