package com.app.faksfit.service;

import com.app.faksfit.model.Faculty;
import com.app.faksfit.model.Role;
import com.app.faksfit.model.User;

public interface IUserService {
    public User findByEmail(String email);

    public void createUser(Long userId, String firstName, String lastName, String email, String password, String dateOfRegistration, Faculty userFaculty, Role userRole);
}
