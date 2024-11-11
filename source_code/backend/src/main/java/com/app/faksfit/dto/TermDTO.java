package com.app.faksfit.dto;

import java.time.LocalDateTime;

public record TermDTO(Integer maxPoints,
                      LocalDateTime termStart,
                      LocalDateTime termEnd,
                      LocationDTO location,
                      ActivityTypeDTO activityType
                      ) {
}
