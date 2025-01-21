package com.app.faksfit.controller;

import com.app.faksfit.dto.StudentSettingsDTO;
import com.app.faksfit.dto.TeacherDashboardDTO;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.Teacher;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.service.impl.TeacherServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/nastavnik")
public class TeacherController {

    private final StudentServiceImpl studentService;
    private final TeacherServiceImpl teacherService;

    public TeacherController(StudentServiceImpl studentService, TeacherServiceImpl teacherService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
    }

    @GetMapping("/svi-studenti")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/current")
    public ResponseEntity<TeacherDashboardDTO> getCurrentTeacher(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        Teacher teacher = teacherService.findByEmail(email);

        if (teacher == null) {
            return ResponseEntity.status(404).body(null);
        }

        TeacherDashboardDTO teacherDashboardDTO = new TeacherDashboardDTO(
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getOfficeLocation()
        );

        return ResponseEntity.ok(teacherDashboardDTO);
    }

}
