package com.app.faksfit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/homePage")
public class HomePageController {
    //wirean servis

    @GetMapping("")
    public String showHomePage(){
        return "Home page";
    }

    @GetMapping("/secure")
    public String secure(){
        return "Congrats Good Pass";
    }
}
