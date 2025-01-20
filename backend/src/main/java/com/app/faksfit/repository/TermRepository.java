package com.app.faksfit.repository;

import com.app.faksfit.model.ActivityLeader;
import com.app.faksfit.model.ActivityType;
import com.app.faksfit.model.Location;
import com.app.faksfit.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TermRepository extends JpaRepository<Term, Long> {
    Term findByTermId(Long termId);

    List<Term> findByTermStartAfter(LocalDateTime date);

    List<Term> findByTermStartBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT t FROM Term t WHERE t.capacity = (SELECT COUNT(st) FROM StudentTerminAssoc st WHERE st.term = t)")
    List<Term> findFullTerms();

    @Query("SELECT t FROM Term t WHERE t.capacity > (SELECT COUNT(st) FROM StudentTerminAssoc st WHERE st.term = t)")
    List<Term> findTermsWithAvailableCapacity();

    // Activity type based
    List<Term> findByActivityTypeTerm(ActivityType activityType);
    List<Term> findByActivityTypeTermAndTermStartAfter(ActivityType activityType, LocalDateTime date);

    // Activity leader based
    List<Term> findByActivityLeaderTerm(ActivityLeader activityLeader);
    List<Term> findByActivityLeaderTermAndTermStartAfter(ActivityLeader activityLeader, LocalDateTime date);

    // Location based
    List<Term> findByLocationTerm(Location location);
    List<Term> findByLocationTermAndTermStartAfter(Location location, LocalDateTime date);

    // Description based
    List<Term> findByTermDescriptionContainingIgnoreCase(String description);

    // Points based
    List<Term> findByMaxPointsGreaterThanEqual(Integer points);

    // Combined
    List<Term> findByActivityTypeTermAndLocationTermAndTermStartAfter(
            ActivityType activityType,
            Location location,
            LocalDateTime date
    );

    @Query("SELECT t FROM Term t WHERE t.capacity > :#{#minCapacity} AND t.termStart > :#{#date}")
    List<Term> findTermsWithMinimumCapacityAfterDate(
            @Param("minCapacity") Integer minCapacity,
            @Param("date") LocalDateTime date
    );

    @Query("SELECT t FROM Term t " +
            "WHERE t.capacity > (SELECT COUNT(sta) FROM StudentTerminAssoc sta WHERE sta.term = t) " +
            "AND t.termStart > :#{#date} " +
            "AND t.activityTypeTerm = :#{#activityType}")
    List<Term> findAvailableTermsByActivityTypeAndDate(
            @Param("activityType") ActivityType activityType,
            @Param("date") LocalDateTime date
    );

    long countByTermStartAfter(LocalDateTime date);
    long countByActivityTypeTerm(ActivityType activityType);

    @Query("SELECT COUNT(t) FROM Term t WHERE t.capacity > " +
            "(SELECT COUNT(sta) FROM StudentTerminAssoc sta WHERE sta.term = t)")
    long countAvailableTerms();

}
