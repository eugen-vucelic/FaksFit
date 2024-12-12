package com.app.faksfit.controller;

import com.app.faksfit.model.Student;
import com.app.faksfit.service.impl.StudentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private StudentServiceImpl studentService;

    @Autowired
    public AuthController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/registration-status")
    public ResponseEntity<?> checkRegistrationStatus(HttpServletRequest request) {
        // Check if the user has an OAuth session but hasn't completed registration
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            Optional<Student> student = Optional.ofNullable(studentService.findByEmail(auth.getName()));
            if (student.isPresent()) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
