package com.app.faksfit.configuration;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.User;
import com.app.faksfit.service.impl.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class CustomOAuth2SuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private StudentServiceImpl studentServiceImpl;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        Student student = studentServiceImpl.findByEmail(email);

        if(student == null){
            response.sendRedirect("/login?error=invalid_user"); //potrebno hendlat na frontu
        }

    }
}
