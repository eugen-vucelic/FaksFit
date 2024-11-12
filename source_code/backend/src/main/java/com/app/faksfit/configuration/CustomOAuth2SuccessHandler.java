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

    @Autowired
    public CustomOAuth2SuccessHandler(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Student student = studentServiceImpl.findByEmail(email);

        if (student == null) {
            assert email != null;
            String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);
            response.sendRedirect("/student/register?email=" + encodedEmail);
        } else {
            response.sendRedirect("/student/dashboard");
        }

    }
}
