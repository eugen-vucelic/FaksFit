package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.ActivityType;
import com.app.faksfit.model.Location;
import com.app.faksfit.model.Term;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TermMapper {
    public List<TermDTO> toTermDTOList(List<Term> terminList){
        List<TermDTO> result = new ArrayList<>();
        for (Term term : terminList) {
            Location location = term.getLocationTerm();
            LocationDTO locationDTO = new LocationDTO(location.getLocationName(), location.getAddress());
            ActivityType activityType = term.getActivityTypeTerm();
            ActivityTypeDTO activityTypeDTO = new ActivityTypeDTO(activityType.getActivityTypeName());
            result.add(new TermDTO(
                    term.getMaxPoints(),
                    term.getTermStart(),
                    term.getTermEnd(),
                    locationDTO,
                    activityTypeDTO
            ));
        }
        return result;
    }
}
