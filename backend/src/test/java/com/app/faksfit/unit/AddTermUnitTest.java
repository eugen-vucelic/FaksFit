package com.app.faksfit.unit;

import com.app.faksfit.controller.ActivityLeaderController;
import com.app.faksfit.dto.NoviTerminDTO;
import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.User;
import com.app.faksfit.service.impl.ActivityLeaderServiceImpl;
import com.app.faksfit.service.impl.TermService;
import com.app.faksfit.service.impl.UserServiceImpl;
import com.app.faksfit.utils.JWTUtil;
import com.app.faksfit.mapper.TermMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AddTermUnitTest {

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private ActivityLeaderServiceImpl activityLeaderService;

    @Mock
    private TermService termService;

    @Mock
    private TermMapper termMapper;

    @InjectMocks
    private ActivityLeaderController activityLeaderController;

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
    void addTermSuccess() {
        NoviTerminDTO noviTerminDTO = new NoviTerminDTO(
                "2025-01-30T10:00:00",
                "2025-01-30T12:00:00",
                12,
                20,
                "Unska 3",
                "Martinovka"
        );
        String token = "Bearer valid.jwt.token";
        String email = "leader@example.com";

        User user = mock(User.class);
        when(user.getUserId()).thenReturn(1L);
        when(user.getEmail()).thenReturn(email);

        ActivityLeader leader = new ActivityLeader();
        leader.setUserId(1L);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(userService.findByEmail(email)).thenReturn(user);
        when(activityLeaderService.getById(user.getUserId())).thenReturn(leader);

        ResponseEntity<String> response = activityLeaderController.addTerm(noviTerminDTO, token);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Term added successfully", response.getBody());

        verify(jwtUtil, times(1)).extractEmail(anyString());
        verify(userService, times(1)).findByEmail(email);
        verify(activityLeaderService, times(1)).getById(user.getUserId());
        verify(termService, times(1)).addTerm(any(), eq(leader));
    }

    @Test
    void addTerm_ActivityLeaderNotFound() {
        NoviTerminDTO noviTerminDTO = new NoviTerminDTO(
                "2025-01-30T10:00:00",
                "2025-01-30T12:00:00",
                12,
                20,
                "Unska 3",
                "Martinovka"
        );
        String token = "Bearer valid.jwt.token";
        String email = "leader@example.com";

        User user = mock(User.class);
        user.setUserId(1L);
        user.setEmail(email);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(userService.findByEmail(email)).thenReturn(user);
        when(activityLeaderService.getById(user.getUserId())).thenReturn(null);

        ResponseEntity<String> response = activityLeaderController.addTerm(noviTerminDTO, token);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(jwtUtil, times(1)).extractEmail(anyString());
        verify(userService, times(1)).findByEmail(email);
        verify(activityLeaderService, times(1)).getById(user.getUserId());
        verify(termService, never()).addTerm(any(), any());
    }

    @Test
    void addTerm_ExceptionThrown() {
        NoviTerminDTO noviTerminDTO = new NoviTerminDTO(
                "2025-01-30T10:00:00",
                "2025-01-30T12:00:00",
                12,
                20,
                "Unska 3",
                "Martinovka"
        );
        String token = "Bearer valid.jwt.token";
        String email = "leader@example.com";

        User user = mock(User.class);
        user.setUserId(1L);
        user.setEmail(email);

        ActivityLeader leader = new ActivityLeader();
        leader.setUserId(1L);

        when(jwtUtil.extractEmail(anyString())).thenReturn(email);
        when(userService.findByEmail(email)).thenReturn(user);
        when(activityLeaderService.getById(user.getUserId())).thenReturn(leader);
        doThrow(new IllegalArgumentException("Invalid term details")).when(termService).addTerm(any(), eq(leader));

        ResponseEntity<String> response = activityLeaderController.addTerm(noviTerminDTO, token);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid term details", response.getBody());

        verify(jwtUtil, times(1)).extractEmail(anyString());
        verify(userService, times(1)).findByEmail(email);
        verify(activityLeaderService, times(1)).getById(user.getUserId());
        verify(termService, times(1)).addTerm(any(), eq(leader));
    }
}
