package com.app.faksfit.controller;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.model.Student;
import com.app.faksfit.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO){

        studentService.addStudent(studentDTO);
        return ResponseEntity.ok("Student added successfully");
    }

    @GetMapping("/svi-studenti")
    public ResponseEntity<List<Student>> getStudents() {
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }
}
