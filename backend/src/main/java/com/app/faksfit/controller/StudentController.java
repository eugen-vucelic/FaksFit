package com.app.faksfit.controller;

import com.app.faksfit.dto.MyPointsDTO;
import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.dto.StudentSettingsDTO;
import com.app.faksfit.mapper.StudentMyPointsMapper;
import com.app.faksfit.mapper.StudentTermMapper;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import com.app.faksfit.dto.TermDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;
    private final StudentTermMapper studentTermMapper;
    private final StudentMyPointsMapper studentMyPointsMapper;
    private final JWTUtil jwtUtil;

    @Autowired
    public StudentController(StudentServiceImpl studentService, StudentTermMapper studentTermMapper, StudentMyPointsMapper studentMyPointsMapper, JWTUtil jwtUtil) {
        this.studentService = studentService;
        this.studentTermMapper = studentTermMapper;
        this.studentMyPointsMapper = studentMyPointsMapper;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createStudent(@RequestBody StudentDTO studentDTO){
        studentService.addStudent(studentDTO);
        return ResponseEntity.ok("Student added successfully");
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
    public ResponseEntity<String> updateStudent(@RequestHeader("Authorization") String token, @RequestBody StudentSettingsDTO studentSettingsDTO){
        String jwt = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwt);

        boolean updated = studentService.updateStudent(email, studentSettingsDTO);
        if (updated) {
            return ResponseEntity.ok("Student updated successfully");
        } else {
            return ResponseEntity.status(404).body("Student not found");
        }
    }

    @GetMapping("/moji-bodovi")
    public ResponseEntity<MyPointsDTO> getResults(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwt);

        Student student = studentService.findByEmail(email);
        MyPointsDTO myPointsDTO = studentMyPointsMapper.mapToMyPointsDTO(student);
        return ResponseEntity.ok(myPointsDTO);
    }

    @GetMapping("/obavijesti")
    public ResponseEntity<Map<String, Object>> getNotifications() {
        List<Map<String, Object>> notifications = List.of(
                Map.of(
                        "title", "Nova obavijest 1",
                        "text", "HMM",
                        "date", "2025-01-10T14:30:00"
                ),
                Map.of(
                        "title", "Nova obavijest 2",
                        "text", "HMMMM",
                        "date", "2025-01-11T10:15:00"
                ),
                Map.of(
                        "title", "Nova obavijest 3",
                        "text", "HMMMMMM",
                        "date", "2025-01-12T13:15:00"
                ),
                Map.of(
                        "title", "Nova obavijest 4",
                        "text", "HMMMMMMMMMM",
                        "date", "2025-01-12T14:40:00"
                )
        );

        return ResponseEntity.ok(Map.of("notifications", notifications));
    }

    @GetMapping("/termini")
    public ResponseEntity<List<TermDTO>> getTerms(@RequestHeader("Authorization") String token) {
        String jwt = token.replace("Bearer ", "");
        String email = jwtUtil.extractEmail(jwt);

        Student student = studentService.findByEmail(email);
        List<StudentTerminAssoc> studentTerms = student.getTerminList();

        List<TermDTO> termDTOList = studentTermMapper.toTermDTOList(studentTerms);

        return ResponseEntity.ok(termDTOList);
    }
}
