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

public class TermDisenrollmentUnitTest {

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
    void removeUserFromTermSuccess() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);

        ResponseEntity<String> response = termController.removeUserFromTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).removeUserFromTerm(termId, student);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User successfully removed from the term", response.getBody());
    }

    @Test
    void removeUserFromTermStudentNotFound() {
        Long termId = 1L;
        String email = "testuser@example.com";
        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(null);

        ResponseEntity<String> response = termController.removeUserFromTerm(termId, oauthUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Student not found", response.getBody());
    }

    @Test
    void removeUserFromTermBadRequest() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);
        doThrow(new IllegalArgumentException("Invalid term")).when(termSignUpService).removeUserFromTerm(termId, student);

        ResponseEntity<String> response = termController.removeUserFromTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).removeUserFromTerm(termId, student);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void removeUserFromTermConflict() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);
        doThrow(new IllegalStateException("Conflict")).when(termSignUpService).removeUserFromTerm(termId, student);

        ResponseEntity<String> response = termController.removeUserFromTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).removeUserFromTerm(termId, student);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void removeUserFromTermInternalError() {
        Long termId = 1L;
        String email = "testuser@example.com";
        Student student = new Student();
        student.setEmail(email);

        when(oauthUser.getAttribute("email")).thenReturn(email);
        when(studentService.findByEmail(email)).thenReturn(student);
        doThrow(new RuntimeException("Unexpected error")).when(termSignUpService).removeUserFromTerm(termId, student);

        ResponseEntity<String> response = termController.removeUserFromTerm(termId, oauthUser);

        verify(termSignUpService, times(1)).removeUserFromTerm(termId, student);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}

