package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StudentTermMapper {

    public List<TermDTO> toTermDTO(List<StudentTerminAssoc> terminList){
        List<TermDTO> result = new ArrayList<>();

        for (StudentTerminAssoc studentTerminAssoc : terminList) {
            if (studentTerminAssoc == null || studentTerminAssoc.getTerm() == null) {
                return null;
            }

            Term term = studentTerminAssoc.getTerm();
            Location location = term.getLocationTerm();
            LocationDTO locationDTO = new LocationDTO(location.getLocationName(), location.getAddress());

            ActivityType activityType = term.getActivityTypeTerm();
            ActivityTypeDTO activityTypeDTO = new ActivityTypeDTO(activityType.getActivityTypeName());

            result.add(new TermDTO(
                    studentTerminAssoc.getTerm().getMaxPoints(),
                    studentTerminAssoc.getTerm().getTermStart(),
                    studentTerminAssoc.getTerm().getTermEnd(),
                    locationDTO,
                    activityTypeDTO
            ));
        }
        return result;
    }
}
