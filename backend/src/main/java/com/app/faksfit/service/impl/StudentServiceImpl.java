package com.app.faksfit.service.impl;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentSettingsDTO;
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

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
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

    @Override
    public boolean updateStudent(String email, StudentSettingsDTO studentSettingsDTO) {
        Student student = studentRepository.findByEmail(email);
        updateNonNullFields(student, studentSettingsDTO);
        studentRepository.save(student);
        return true;
    }


    private void updateNonNullFields(Student student, StudentSettingsDTO studentSettingsDTO) {
        if (studentSettingsDTO.firstName() != null) {
            student.setFirstName(studentSettingsDTO.firstName());
        }
        if (studentSettingsDTO.lastName() != null) {
            student.setLastName(studentSettingsDTO.lastName());
        }
        if (studentSettingsDTO.gender() != null) {
            student.setGender(studentSettingsDTO.gender());
        }
        if (studentSettingsDTO.nationality() != null) {
            student.setNationality(studentSettingsDTO.nationality());
        }
        if (studentSettingsDTO.birthDate() != null) {
            student.setBirthDate(studentSettingsDTO.birthDate());
        }
        if (studentSettingsDTO.phoneNumber() != null) {
            student.setPhoneNumber(studentSettingsDTO.phoneNumber());
        }
        if (studentSettingsDTO.semester() != null) {
            student.setSemester(studentSettingsDTO.semester());
        }
    }
}

