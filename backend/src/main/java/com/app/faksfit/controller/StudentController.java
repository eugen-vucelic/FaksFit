package com.app.faksfit.controller;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentSettingsDTO;
import com.app.faksfit.model.Student;
import com.app.faksfit.service.impl.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/current")
    public ResponseEntity<StudentSettingsDTO> getCurrentStudent(@AuthenticationPrincipal OAuth2User oauthUser) {
        String email = oauthUser.getAttribute("email");
        Student student = studentService.findByEmail(email);

        if (student == null) {
            return ResponseEntity.status(404).body(null);
        }

        StudentSettingsDTO studentSettingsDTO = new StudentSettingsDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getGender(),
                student.getNationality(),
                student.getBirthDate(),
                student.getPhoneNumber(),
                student.getSemester()
        );

        return ResponseEntity.ok(studentSettingsDTO);
    }

    @PatchMapping("/patch")
    public ResponseEntity<String> updateStudent(@AuthenticationPrincipal OAuth2User oauthUser, @RequestBody StudentSettingsDTO studentSettingsDTO){
        String email = oauthUser.getAttribute("email");
        boolean updated = studentService.updateStudent(email, studentSettingsDTO);
        if (updated) {
            return ResponseEntity.ok("Student updated successfully");
        } else {
            return ResponseEntity.status(404).body("Student not found");
        }
    }
}
