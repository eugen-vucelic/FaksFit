package com.app.faksfit.service.impl;

import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.repository.ActivityLeaderRepository;
import com.app.faksfit.service.IActivityLeaderService;
import org.springframework.stereotype.Service;

@Service
public class ActivityLeaderServiceImpl implements IActivityLeaderService {

    private final ActivityLeaderRepository activityLeaderRepository;

    public ActivityLeaderServiceImpl(ActivityLeaderRepository activityLeaderRepository) {
        this.activityLeaderRepository = activityLeaderRepository;
    }

    @Override
    public ActivityLeader getById(Long id) {
        return activityLeaderRepository.getById(id);
    }
}
