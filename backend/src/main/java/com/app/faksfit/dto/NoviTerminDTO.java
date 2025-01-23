package com.app.faksfit.dto;

public record NoviTerminDTO( String termStart,
                             String termEnd,
                             Integer maxPoints,
                             Integer capacity,
                             String location,
                             String locationName) {
}
