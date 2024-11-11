package com.app.faksfit.service.impl;

import com.app.faksfit.model.Student;
import com.app.faksfit.model.User;
import com.app.faksfit.repository.UserRepository;
import com.app.faksfit.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
