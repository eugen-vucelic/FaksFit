package com.app.faksfit.mapper;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.model.*;
import com.app.faksfit.repository.FacultyRepository;
import com.app.faksfit.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class StudentMapper {

    private final FacultyRepository facultyRepository;

    private final TeacherRepository teacherRepository;

    @Autowired
    public StudentMapper(FacultyRepository facultyRepository, TeacherRepository teacherRepository) {
        this.facultyRepository = facultyRepository;
        this.teacherRepository = teacherRepository;
    }


    public Student toEntity(StudentDTO dto) {
        Student student = new Student();

        Faculty studentFaculty = facultyRepository.findByFacultyName(dto.userFaculty());
        Teacher randomTeacher = teacherRepository.findRandomTeacherByFaculty(studentFaculty).get(0);

        student.setFirstName(dto.firstName());
        student.setLastName(dto.lastName());
        student.setEmail(dto.email());
        student.setUserFaculty(studentFaculty);
        student.setUserRole(Role.STUDENT);

        student.setJMBAG(dto.JMBAG());
        student.setSemester(dto.semester());

        student.setGender("N/A");
        student.setNationality("N/A");
        student.setBirthDate(LocalDate.of(1970, 1, 1));
        student.setPhoneNumber("N/A");

        student.setTotalPoints(0);
        student.setPassStatus(false);
        student.setAcademicYear(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy"))); // Set as needed

        student.setStudentTeacher(randomTeacher);

        student.setDateOfRegistration(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        student.setTerminList(null);
        student.setNotificationList(null);

        return student;
    }
}
