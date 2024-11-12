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

    private final StudentServiceImpl studentService;

    @Autowired
    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO){
        //Otkomentiraj za provjeru na konzoli dal se primaju dobri podaci
        //System.out.println("First Name: " + studentDTO.firstName());
        //System.out.println("Last Name: " + studentDTO.lastName());
        //System.out.println("Email: " + studentDTO.email());
        //System.out.println("JMBAG: " + studentDTO.JMBAG());
        //System.out.println("Fakultet: " + studentDTO.userFaculty());
        //System.out.println("Semestar: " + studentDTO.semester());

        studentService.addStudent(studentDTO);
        return ResponseEntity.ok("Student added successfully");
    }
}
