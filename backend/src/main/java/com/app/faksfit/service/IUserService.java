package com.app.faksfit.service;

import com.app.faksfit.model.User;

import java.util.List;

public interface IUserService {

    User getById(Long id);

    User findByEmail(String email);

}
