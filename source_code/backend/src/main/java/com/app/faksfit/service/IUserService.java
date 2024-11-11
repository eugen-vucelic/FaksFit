package com.app.faksfit.service;

import com.app.faksfit.model.User;

public interface IUserService {
    User findByEmail(String email);
}
