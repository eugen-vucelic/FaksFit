package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityLeaderDashboardDTO;
import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.*;
import com.app.faksfit.repository.ActivityTypeRepository;
import com.app.faksfit.repository.TermRepository;
import com.app.faksfit.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityLeaderDashboardMapper {

    private final UserRepository userRepository;
    private final TermRepository termRepository;
    private final ActivityTypeRepository activityTypeRepository;

    public ActivityLeaderDashboardMapper(UserRepository userRepository, TermRepository termRepository, ActivityTypeRepository activityTypeRepository) {
        this.userRepository = userRepository;
        this.termRepository = termRepository;
        this.activityTypeRepository = activityTypeRepository;
    }

    public ActivityLeaderDashboardDTO toActivityLeaderDashboardDTO(ActivityLeader activityLeader){
        User user = userRepository.getById(activityLeader.getUserId());
        return new ActivityLeaderDashboardDTO(
                user.getFirstName(),
                user.getLastName(),
                activityLeader.getLeaderActivityType(),
                toTermDTOList(activityLeader.getActivityLeaderTerms())

        );
    }
    public List<TermDTO> toTermDTOList(List<Term> terms) {
        return terms.stream()
                .map(this::toTermDTO)
                .collect(Collectors.toList());
    }

    public TermDTO toTermDTO(Term term){
        return new TermDTO(
                term.getMaxPoints(),
                term.getTermStart(),
                term.getTermEnd(),
                toLocationDTO(term.getLocationTerm()),
                toActivityTypeDTO(term.getActivityTypeTerm()),
                term.getTermId()
        );
    }
    public LocationDTO toLocationDTO(Location location){
        return new LocationDTO(
                location.getLocationName(),
                location.getAddress()
        );

    }
    public ActivityTypeDTO toActivityTypeDTO(ActivityType activityType){
        return new ActivityTypeDTO(
                activityType.getActivityTypeName()
        );
    }
}
