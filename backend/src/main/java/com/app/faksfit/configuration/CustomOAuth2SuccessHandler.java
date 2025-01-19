package com.app.faksfit.configuration;

import com.app.faksfit.model.Role;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.User;
import com.app.faksfit.service.impl.StudentServiceImpl;
import com.app.faksfit.service.impl.UserServiceImpl;
import com.app.faksfit.utils.JWTUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final StudentServiceImpl studentServiceImpl;
    private final UserServiceImpl userService;
    private final JWTUtil jwtUtil;
    private final boolean production = false;

    private final String FRONTEND_URL = production ? "https://faksfit-7du1.onrender.com" : "http://localhost:5173";

    @Autowired
    public CustomOAuth2SuccessHandler(StudentServiceImpl studentServiceImpl, UserServiceImpl userService, JWTUtil jwtUtil) {
        this.studentServiceImpl = studentServiceImpl;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Student student = studentServiceImpl.findByEmail(email);
        User user = userService.findByEmail(email);

        if (student == null && user == null) {
            assert email != null;
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            response.sendRedirect(FRONTEND_URL + "/registracija?email=" + encodedEmail);
        } else {
            Role role = user.getUserRole();
            String jwtToken = jwtUtil.generateToken(email, role);
            response.sendRedirect(FRONTEND_URL + "/student/dashboard?token=" + jwtToken);
        }
    }
}