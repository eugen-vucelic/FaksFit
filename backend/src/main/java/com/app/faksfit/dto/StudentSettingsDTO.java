package com.app.faksfit.dto;

import java.time.LocalDate;

public record StudentSettingsDTO(
        String firstName,
        String lastName,
        String gender,
        String nationality,
        LocalDate birthDate,
        String phoneNumber,
        String semester
) {
}
