package com.app.faksfit.service;


import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.ActivityLeader;

public interface IActivityLeaderService extends IUserService {

    @Override
    ActivityLeader getById(Long id);

    @Override
    ActivityLeader findByEmail(String email);

    void addTerm(TermDTO termDTO, ActivityLeader activityLeader);

    void removeTerm(Long termId, ActivityLeader activityLeader);

    void updateTerm(Long termId, TermDTO termDTO, ActivityLeader activityLeader);
}
