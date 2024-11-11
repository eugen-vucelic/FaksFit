package com.app.faksfit.controller;

import com.app.faksfit.dto.StudentDashboardDTO;
import com.app.faksfit.mapper.StudentDashboardMapper;
import com.app.faksfit.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/student/{id}")
    public ResponseEntity<StudentDashboardDTO> getStudentDashboard(@PathVariable Long id) {
        StudentDashboardDTO studentDashboardDTO = studentDashboardMapper.toStudentDashboardDTO(studentService.getById(id));
        return ResponseEntity.ok(studentDashboardDTO);
    }
}
