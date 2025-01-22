package com.app.faksfit.dto;

public record StudentTeacherDTO(String firstName,
                         String lastName,
                         String email,
                         String JMBAG,
                         String semester,
                         Integer totalPoints
) {
}