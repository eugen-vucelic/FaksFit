package com.app.faksfit.controller;

import com.app.faksfit.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping("/register")
//    public ResponseEntity<String> createUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
//        userService.createUser(userRegistrationDTO); //createUser koji prima userRegistrationDTO treba
//        return ResponseEntity.status(HttpStatus.CREATED);
//    }
}
