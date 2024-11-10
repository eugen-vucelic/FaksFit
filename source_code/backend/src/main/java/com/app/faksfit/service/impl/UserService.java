package com.app.faksfit.service.impl;

import com.app.faksfit.model.Faculty;
import com.app.faksfit.model.Role;
import com.app.faksfit.model.User;
import com.app.faksfit.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public void createUser(Long userId, String firstName, String lastName, String email, String password, String dateOfRegistration, Faculty userFaculty, Role userRole) {

    }
}
