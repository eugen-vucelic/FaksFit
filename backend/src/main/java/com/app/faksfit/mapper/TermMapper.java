package com.app.faksfit.mapper;

import com.app.faksfit.dto.ActivityTypeDTO;
import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.dto.NoviTerminDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.*;
import com.app.faksfit.repository.ActivityTypeRepository;
import com.app.faksfit.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TermMapper {

    private final LocationMapper locationMapper;
    private final ActivityTypeMapper activityTypeMapper;
    private final ActivityTypeRepository activityTypeRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public TermMapper(LocationMapper locationMapper, ActivityTypeMapper activityTypeMapper, ActivityTypeRepository activityTypeRepository, LocationRepository locationRepository) {
        this.locationMapper = locationMapper;
        this.activityTypeMapper = activityTypeMapper;
        this.activityTypeRepository = activityTypeRepository;
        this.locationRepository = locationRepository;
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
    public Term toEntityNewTerm(TermDTO termDTO, ActivityLeader activityLeader) {
        Location location = locationMapper.toEntity(termDTO.location());
        locationRepository.save(location);
        Term term = new Term();
        term.setMaxPoints(termDTO.maxPoints());
        term.setTermStart(termDTO.termStart());
        term.setTermEnd(termDTO.termEnd());
        term.setLocationTerm(location);
        term.setActivityTypeTerm(activityTypeRepository.findByActivityTypeName(termDTO.activityType().activityTypeName()));
        term.setActivityLeaderTerm(activityLeader);
        term.setCapacity(termDTO.capacity());

        return term;
    }

    public TermDTO toTermDTO(NoviTerminDTO noviTerminDTO, ActivityLeader activityLeader){
        LocationDTO locationDTO = new LocationDTO(noviTerminDTO.locationName(),
                noviTerminDTO.location().split(",")[0].trim());
        ActivityTypeDTO activityTypeDTO = new ActivityTypeDTO(activityLeader.getLeaderActivityType().getActivityTypeName());

        TermDTO termDTO = new TermDTO(noviTerminDTO.maxPoints(),
                LocalDateTime.parse(noviTerminDTO.termStart()),
                LocalDateTime.parse(noviTerminDTO.termEnd()),
                locationDTO,
                activityTypeDTO,
                noviTerminDTO.capacity(),
                0L);
        return termDTO;
    }
}
