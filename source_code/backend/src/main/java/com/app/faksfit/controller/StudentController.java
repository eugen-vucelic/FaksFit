package com.app.faksfit.controller;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/student")
public class StudentController {

//    private final StudentServiceImpl studentService;
//
//    @Autowired
//    public StudentController(StudentServiceImpl studentService) {
//        this.studentService = studentService;
//    }

    @PostMapping("/register")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO){

        //studentService.addStudent(studentDTO);
        return ResponseEntity.ok("Student added successfully");
    }
}
