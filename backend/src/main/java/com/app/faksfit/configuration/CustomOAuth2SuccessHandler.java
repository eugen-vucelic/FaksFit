package com.app.faksfit.configuration;

import com.app.faksfit.model.Student;
import com.app.faksfit.service.impl.StudentServiceImpl;
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

    private static final String FRONTEND_URL = "https://faksfit-7du1.onrender.com";

    @Autowired
    public CustomOAuth2SuccessHandler(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");

            if (email == null) {
                response.sendRedirect(FRONTEND_URL + "/error?message=" +
                        URLEncoder.encode("Email not provided", StandardCharsets.UTF_8));
                return;
            }

            Student student = studentServiceImpl.findByEmail(email);

            if (student == null) {
                String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
                response.sendRedirect(FRONTEND_URL + "/registracija?email=" + encodedEmail);
            } else {
                response.sendRedirect(FRONTEND_URL + "/dashboard/student");
            }
        } catch (Exception e) {
            response.sendRedirect(FRONTEND_URL + "/error?message=" +
                    URLEncoder.encode("Authentication failed", StandardCharsets.UTF_8));
        }
    }
}