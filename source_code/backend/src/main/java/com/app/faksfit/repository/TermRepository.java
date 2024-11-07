package com.app.faksfit.repository;

import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.Location;
import com.app.faksfit.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    Term findByTermId(Long termId);

    List<Term> findByTermStartBetween(LocalDateTime start, LocalDateTime end);

    List<Term> findByLocationTerm(Location location);

    List<Term> findByActivityLeaderTerm(ActivityLeader activityLeader);

    @Query("SELECT t FROM Term t WHERE t.capacity = (SELECT COUNT(st) FROM StudentTerminAssoc st WHERE st.term = t)")
    List<Term> findFullTerms();

    @Query("SELECT t FROM Term t WHERE t.capacity > (SELECT COUNT(st) FROM StudentTerminAssoc st WHERE st.term = t)")
    List<Term> findTermsWithAvailableCapacity();

    boolean existsByTermStartBetween(LocalDateTime start, LocalDateTime end);
}
