package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.model.ActivityType;
import org.springframework.stereotype.Component;

@Component
public class ActivityTypeMapper {
    public ActivityType toEntity(ActivityTypeDTO activityTypeDTO) {
        ActivityType activityType = new ActivityType();
        activityType.setActivityTypeName(activityTypeDTO.activityTypeName());
        return activityType;
    }
}
