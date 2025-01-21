package com.app.faksfit.dto;

import com.app.faksfit.model.ActivityType;

import java.util.List;

public record ActivityLeaderDashboardDTO(
        String firstName,
        String lastName,
        ActivityType activity,
        List<TermDTO> termini

) {
}
