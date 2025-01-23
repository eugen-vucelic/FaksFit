package com.app.faksfit.service.impl;

import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.mapper.TermMapper;
import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.ActivityType;
import com.app.faksfit.model.Location;
import com.app.faksfit.model.Term;
import com.app.faksfit.repository.ActivityTypeRepository;
import com.app.faksfit.repository.LocationRepository;
import com.app.faksfit.repository.TermRepository;
import com.app.faksfit.service.ITermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TermService implements ITermService {
    private final TermRepository termRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final LocationRepository locationRepository;
    private final TermMapper termMapper;

    @Autowired
    public TermService(TermRepository termRepository, ActivityTypeRepository activityTypeRepository, LocationRepository locationRepository, TermMapper termMapper) {
        this.termRepository = termRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.locationRepository = locationRepository;
        this.termMapper = termMapper;
    }

    @Override
    public List<Term> getAllTerms() {
        return termRepository.findAll();
    }

    @Override
    public List<Term> getTermsAfterNow() {
        return termRepository.findByTermStartAfter(LocalDateTime.now());
    }

    @Override
    public List<Term> getAvailableTerms() {
        return termRepository.findTermsWithAvailableCapacity();
    }

    @Override
    public List<Term> searchTermsBySubject(String subject) {
        ActivityType activityType = activityTypeRepository.findByActivityTypeName(subject);
        return termRepository.findByActivityTypeTerm(activityType);
    }

    @Override
    public List<Term> getTermsBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return termRepository.findByTermStartBetween(startDate, endDate);
    }

    @Override
    public List<Term> getTermsByLocation(String location) {
        Location locationEntity = locationRepository.findByLocationName(location);
        return termRepository.findByLocationTerm(locationEntity);
    }

    @Override
    public List<Term> getTermsBySubjectAferNow(String subject) {
        ActivityType activityType = activityTypeRepository.findByActivityTypeName(subject);
        return termRepository.findByActivityTypeTermAndTermStartAfter(activityType, LocalDateTime.now());
    }

    @Override
    public List<Term> getTermsByActivityLeaderUserId(Long activityLeaderUserId) {
        return termRepository.findByActivityLeaderTermUserId(activityLeaderUserId);
    }


    @Override
    public Term getTermById(Long id) {
        return termRepository.findByTermId(id);
    }

    @Override
    public Term saveTerm(Term term) {
        return termRepository.save(term);
    }

    @Override
    public void deleteTerm(Long id) {
        termRepository.deleteById(id);
    }


    @Override
    public void incrementCapacity(Long termId) {
        Term term = termRepository.findByTermId(termId);
        term.setCapacity(term.getCapacity() + 1);
        termRepository.save(term);
    }

    @Override
    public void decrementCapacity(Long termId) {
        Term term = termRepository.findByTermId(termId);
        term.setCapacity(term.getCapacity() - 1);
        termRepository.save(term);
    }

    @Override
    public int getCapacity(Long termId) {
        Term term = termRepository.findByTermId(termId);
        return term.getCapacity();
    }

    @Override
    public void addTerm(TermDTO termDTO, ActivityLeader activityLeader) {
        termRepository.save(termMapper.toEntity(termDTO, activityLeader));
    }

}
