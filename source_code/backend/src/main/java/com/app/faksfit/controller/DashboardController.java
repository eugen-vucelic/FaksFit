package com.app.faksfit.controller;

import com.app.faksfit.dto.StudentDashboardDTO;
import com.app.faksfit.mapper.StudentDashboardMapper;
import com.app.faksfit.model.Student;
import com.app.faksfit.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final StudentServiceImpl studentService;

    private final StudentDashboardMapper studentDashboardMapper;


    @Autowired
    public DashboardController(StudentServiceImpl studentService, StudentDashboardMapper studentDashboardMapper) {
        this.studentService = studentService;
        this.studentDashboardMapper = studentDashboardMapper;
    }

    @GetMapping("/student")
    public ResponseEntity<StudentDashboardDTO> getStudentDashboard(@AuthenticationPrincipal OAuth2User oauthUser) {

        String email = oauthUser.getAttribute("email");

        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        StudentDashboardDTO dashboardDTO = studentDashboardMapper.toStudentDashboardDTO(student);

        return ResponseEntity.ok(dashboardDTO);
    }
}
