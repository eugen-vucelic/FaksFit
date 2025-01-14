package com.app.faksfit.dto;

import java.time.LocalDateTime;
import java.util.List;

public record TerminDTO(String activityLeaderID,
                      String activityType,
                      String date,
                      String start,
                      String end,
                      String location,
                      Integer maxCapacity,
                      String capacity,
                      List<Long>listOfStudentsIDs,
                      Integer maxPoints

) {
}