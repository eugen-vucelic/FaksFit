package com.app.faksfit.unit;

import com.app.faksfit.controller.TermController;
import com.app.faksfit.model.Student;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.service.impl.TermSignUpServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TermEnrollmentUnitTest {

    @InjectMocks
    private TermController termController;

    @Mock
    private StudentServiceImpl studentService;

    @Mock
    private TermSignUpServiceImpl termSignUpService;

    @Mock
    private OAuth2User oauthUser;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void addUserTermSuccess() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);

        ResponseEntity<String> response = termController.addUserTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).addUserToTerm(termId, student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully added to the term", response.getBody());
    }

    @Test
    void addUserTermStudentNotFound() {
        Long termId = 1L;
        String email = "testuser@example.com";
        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(null);

        ResponseEntity<String> response = termController.addUserTerm(termId, oauthUser);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void addUserTermBadRequest() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);
        doThrow(new IllegalArgumentException("Invalid term")).when(termSignUpService).addUserToTerm(termId, student);

        ResponseEntity<String> response = termController.addUserTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).addUserToTerm(termId, student);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid term", response.getBody());
    }

    @Test
    void addUserTermConflict() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);
        doThrow(new IllegalStateException("Conflict")).when(termSignUpService).addUserToTerm(termId, student);

        ResponseEntity<String> response = termController.addUserTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).addUserToTerm(termId, student);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}