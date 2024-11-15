package com.app.faksfit.repository;

import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.ActivityType;
import com.app.faksfit.model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityLeaderRepository extends JpaRepository<ActivityLeader, Long> {
    ActivityLeader findByEmail(String email);

    List<ActivityLeader> findByLeaderActivityType(ActivityType activityType);

    List<ActivityLeader> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmail(String email);

    List<ActivityLeader> findByUserFaculty(Faculty faculty);

    List<ActivityLeader> findByActivityLeaderTermsIsNotEmpty();
}
