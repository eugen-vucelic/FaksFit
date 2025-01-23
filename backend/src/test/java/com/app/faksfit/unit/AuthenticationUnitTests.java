package com.app.faksfit.unit;


import com.app.faksfit.configuration.CustomOAuth2SuccessHandler;
import com.app.faksfit.model.Role;
import com.app.faksfit.model.User;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.service.impl.UserServiceImpl;
import com.app.faksfit.utils.JWTUtil;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.mockito.Mockito.*;

public class AuthenticationUnitTests {

    @Test
    void testOnAuthenticationSuccessNewUser() throws Exception {
        StudentServiceImpl studentService = mock(StudentServiceImpl.class);
        UserServiceImpl userService = mock(UserServiceImpl.class);
        JWTUtil jwtUtil = mock(JWTUtil.class);

        CustomOAuth2SuccessHandler successHandler = new CustomOAuth2SuccessHandler(studentService, userService, jwtUtil);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        OAuth2User oauth2User = mock(OAuth2User.class);
        when(authentication.getPrincipal()).thenReturn(oauth2User);
        when(oauth2User.getAttribute("email")).thenReturn("newuser@example.com");

        when(studentService.findByEmail("newuser@example.com")).thenReturn(null);
        when(userService.findByEmail("newuser@example.com")).thenReturn(null);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(response).sendRedirect(contains("/registracija?email=newuser%40example.com"));
    }

    @Test
    void testOnAuthenticationSuccessRegisteredUser() throws Exception {
        StudentServiceImpl studentService = mock(StudentServiceImpl.class);
        UserServiceImpl userService = mock(UserServiceImpl.class);
        JWTUtil jwtUtil = mock(JWTUtil.class);

        CustomOAuth2SuccessHandler successHandler = new CustomOAuth2SuccessHandler(studentService, userService, jwtUtil);

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Authentication authentication = mock(Authentication.class);

        OAuth2User oauth2User = mock(OAuth2User.class);
        when(authentication.getPrincipal()).thenReturn(oauth2User);

        String email = "registereduser@example.com";
        String jwtToken = "mockJwtToken";
        when(oauth2User.getAttribute("email")).thenReturn(email);

        User mockUser = mock(User.class);
        when(mockUser.getEmail()).thenReturn(email);
        when(mockUser.getUserRole()).thenReturn(Role.STUDENT);

        when(studentService.findByEmail(email)).thenReturn(null);
        when(userService.findByEmail(email)).thenReturn(mockUser);
        when(jwtUtil.generateToken(email, Role.STUDENT)).thenReturn(jwtToken);

        successHandler.onAuthenticationSuccess(request, response, authentication);

        verify(jwtUtil).generateToken(email, Role.STUDENT);
        verify(response).sendRedirect(contains("/?token=" + jwtToken));
    }
}
