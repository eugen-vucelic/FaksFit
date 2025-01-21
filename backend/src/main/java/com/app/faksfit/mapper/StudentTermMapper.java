package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class StudentTermMapper {

    public LocationDTO mapLocation(Location location) {
        if (location == null) {
            return null;
        }

        return new LocationDTO(
                location.getLocationName(),
                location.getAddress()
        );
    }


    public ActivityTypeDTO mapActivityType(ActivityType activityType) {
        if (activityType == null) {
            return null;
        }

        return new ActivityTypeDTO(
                activityType.getActivityTypeName()
        );
    }

    public TermDTO map(StudentTerminAssoc studentTerminAssoc) {
        if (studentTerminAssoc == null || studentTerminAssoc.getTerm() == null) {
            return null;
        }

        return new TermDTO(
                studentTerminAssoc.getTerm().getMaxPoints(),
                studentTerminAssoc.getTerm().getTermStart(),
                studentTerminAssoc.getTerm().getTermEnd(),
                mapLocation(studentTerminAssoc.getTerm().getLocationTerm()),
                mapActivityType(studentTerminAssoc.getTerm().getActivityTypeTerm()),
                studentTerminAssoc.getTerm().getTermId()
        );
    }

    public List<TermDTO> toTermDTOList(List<StudentTerminAssoc> terminList){
        if (terminList == null) {
            return List.of();
        }

        return terminList.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }
}
