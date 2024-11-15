package com.app.faksfit.dto;

import java.util.List;

public record StudentDashboardDTO(String firstName,
                                  String lastName,
                                  String JMBAG,
                                  Boolean passStatus,
                                  List<TermDTO> terminList,
                                  TeacherDTO teacher,
                                  Integer totalPoints) {
}
