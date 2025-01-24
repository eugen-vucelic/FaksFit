package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityLeaderDashboardDTO;
import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.*;
import com.app.faksfit.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActivityLeaderDashboardMapper {

    private final UserRepository userRepository;

    public ActivityLeaderDashboardMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ActivityLeaderDashboardDTO toActivityLeaderDashboardDTO(ActivityLeader activityLeader){
        User user = userRepository.findByUserId(activityLeader.getUserId());
        return new ActivityLeaderDashboardDTO(
                user.getFirstName(),
                user.getLastName(),
                activityLeader.getLeaderActivityType().getActivityTypeName(),
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
                term.getCapacity(),
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
