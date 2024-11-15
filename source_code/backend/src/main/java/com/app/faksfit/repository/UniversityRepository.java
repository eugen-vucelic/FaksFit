package com.app.faksfit.repository;

import com.app.faksfit.model.University;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface UniversityRepository extends JpaRepository<University, Long> {
        University findByUniversityName(String universityName);

        University findByUniversityId(Long universityId);
}
