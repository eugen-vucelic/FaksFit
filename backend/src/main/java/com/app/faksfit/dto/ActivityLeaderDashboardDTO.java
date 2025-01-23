package com.app.faksfit.dto;

import java.util.List;

public record ActivityLeaderDashboardDTO(
        String firstName,
        String lastName,
        String activityTypeName,
        List<TermDTO> termini

) {
}
