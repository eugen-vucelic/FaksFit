package com.app.faksfit.repository;

import com.app.faksfit.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {

    Faculty findByFacultyName(String facultyName);

    Faculty findByFacultyId(Long facultyId);
}
