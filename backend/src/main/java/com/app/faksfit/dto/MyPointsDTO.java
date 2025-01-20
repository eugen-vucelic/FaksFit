package com.app.faksfit.dto;

import java.util.List;

public record MyPointsDTO(String status, int totalPoints, int progress, List<MyPointsTermDTO> points) {
}
