package com.app.faksfit.service;

import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.Term;

import java.time.LocalDateTime;
import java.util.List;

public interface ITermService {
    List<Term> getAllTerms();
    List<Term> getTermsAfterNow();
    List<Term> getAvailableTerms();
    List<Term> searchTermsBySubject(String subject);
    List<Term> getTermsBetweenDates(LocalDateTime startDate, LocalDateTime endDate);
    List<Term> getTermsByLocation(String location);
    List<Term> getTermsBySubjectAferNow(String subject);
    List<Term> getTermsByActivityLeaderUserId(Long activityLeaderUserId);

    Term getTermById(Long id);
    Term saveTerm(Term term);
    void deleteTerm(Long id);

    void incrementCapacity(Long termId);
    void decrementCapacity(Long termId);
    int getCapacity(Long termId);
    void addTerm(TermDTO termDTO, ActivityLeader activityLeader);
}
