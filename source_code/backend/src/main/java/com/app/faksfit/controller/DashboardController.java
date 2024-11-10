package com.app.faksfit.controller;

import com.app.faksfit.service.impl.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/{userId}")
    public StudentDashboardDTO showStudentInfo(Long userId){
        return dashboardService.showStudentInfo(userId);
    }

}
