package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TermMapper {

    private final LocationMapper locationMapper;
    private final ActivityTypeMapper activityTypeMapper;

    @Autowired
    public TermMapper(LocationMapper locationMapper, ActivityTypeMapper activityTypeMapper) {
        this.locationMapper = locationMapper;
        this.activityTypeMapper = activityTypeMapper;
    }

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
                    activityTypeDTO,
                    term.getCapacity(),
                    term.getTermId()
            ));
        }
        return result;
    }

    public Term toEntity(TermDTO termDTO, ActivityLeader activityLeader) {
        Term term = new Term();
        term.setMaxPoints(termDTO.maxPoints());
        term.setTermStart(termDTO.termStart());
        term.setTermEnd(termDTO.termEnd());
        term.setLocationTerm(locationMapper.toEntity(termDTO.location()));
        term.setActivityTypeTerm(activityTypeMapper.toEntity(termDTO.activityType()));
        term.setActivityLeaderTerm(activityLeader);
        term.setCapacity(termDTO.capacity());

        return term;
    }
}
