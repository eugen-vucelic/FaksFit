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

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    private StudentServiceImpl studentService;

    @Autowired
    public AuthController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> checkUserRegistrationStatus(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String email = auth.getName();
            Optional<Student> student = Optional.ofNullable(studentService.findByEmail(email));

            if (student.isPresent()) {
                return ResponseEntity.ok(Map.of("registrationComplete", true));
            } else {
                return ResponseEntity.ok(Map.of("registrationComplete", false));
            }
        }

        // User not authenticated
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
