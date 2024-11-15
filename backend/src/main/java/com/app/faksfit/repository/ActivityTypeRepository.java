package com.app.faksfit.repository;

import com.app.faksfit.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {

    ActivityType findByActivityTypeId(Long activityTypeId);

    ActivityType findByActivityTypeName(String activityTypeName);

    boolean existsByActivityTypeName(String activityTypeName);

    List<ActivityType> findByActivityTypeNameContaining(String keyword);

}
