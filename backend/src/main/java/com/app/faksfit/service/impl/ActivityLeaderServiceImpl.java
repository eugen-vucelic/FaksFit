package com.app.faksfit.service.impl;

import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.mapper.ActivityTypeMapper;
import com.app.faksfit.mapper.LocationMapper;
import com.app.faksfit.mapper.TermMapper;
import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.Term;
import com.app.faksfit.repository.ActivityLeaderRepository;
import com.app.faksfit.repository.TermRepository;
import com.app.faksfit.repository.UserRepository;
import com.app.faksfit.service.IActivityLeaderService;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ActivityLeaderServiceImpl implements IActivityLeaderService {

    private final ActivityLeaderRepository activityLeaderRepository;
    private final UserRepository userRepository;
    private final TermRepository termRepository;
    private final TermMapper termMapper;
    private final LocationMapper locationMapper;
    private final ActivityTypeMapper activityTypeMapper;

    public ActivityLeaderServiceImpl(ActivityLeaderRepository activityLeaderRepository, UserRepository userRepository, TermRepository termRepository, TermMapper termMapper, LocationMapper locationMapper, ActivityTypeMapper activityTypeMapper) {
        this.activityLeaderRepository = activityLeaderRepository;
        this.userRepository = userRepository;
        this.termRepository = termRepository;
        this.termMapper = termMapper;
        this.locationMapper = locationMapper;
        this.activityTypeMapper = activityTypeMapper;
    }

    @Override
    public ActivityLeader getById(Long id) {
        return (ActivityLeader) userRepository.findByUserId(id);
    }

    @Override
    public ActivityLeader findByEmail(String email) {
        return activityLeaderRepository.findByEmail(email);
    }

    @Override
    public void addTerm(TermDTO termDTO, ActivityLeader activityLeader) {
        if (activityLeader == null) {
            throw new IllegalArgumentException("ActivityLeader must not be null");
        }

        try {
            Term term = termMapper.toEntity(termDTO, activityLeader);

            activityLeader.getActivityLeaderTerms().add(term);

            activityLeaderRepository.save(activityLeader);
            termRepository.save(term);

        } catch (Exception e) {
            throw new RuntimeException("Greška pri spremanju podataka", e);
        }
    }

    @Override
    public void removeTerm(Long termId, ActivityLeader activityLeader) {
        if (termId == null || activityLeader == null) {
            throw new IllegalArgumentException("TermId and activityLeader must not be null");
        }

        Term term = termRepository.findByTermId(termId);
        if (term == null) {
            throw new IllegalArgumentException("Termin s tim id-om ne postoji!");
        }

        try {
            activityLeader.getActivityLeaderTerms().remove(term);
            activityLeaderRepository.save(activityLeader);
            termRepository.delete(term);

        } catch (DataAccessException e) {
            throw new RuntimeException("Greška pri spremanju podataka", e);
        }
    }

    @Override
    public void updateTerm(Long termId, TermDTO termDTO, ActivityLeader activityLeader) {
        if (termId == null || termDTO == null || activityLeader == null) {
            throw new IllegalArgumentException("TermId, TermDTO, and ActivityLeader must not be null");
        }

        Term term = termRepository.findByTermId(termId);
        if (term == null) {
            throw new IllegalArgumentException("Termin s tim ID-om ne postoji!");
        }

        if (!term.getActivityLeaderTerm().equals(activityLeader)) {
            throw new IllegalArgumentException("Termin ne pripada datom voditelju aktivnosti!");
        }

        try {
            term.setMaxPoints(termDTO.maxPoints());
            term.setTermStart(termDTO.termStart());
            term.setTermEnd(termDTO.termEnd());
            term.setLocationTerm(locationMapper.toEntity(termDTO.location()));
            term.setActivityTypeTerm(activityTypeMapper.toEntity(termDTO.activityType()));

            termRepository.save(term);

        } catch (DataAccessException e) {
            throw new RuntimeException("Greška pri ažuriranju termina", e);
        }
    }

}
