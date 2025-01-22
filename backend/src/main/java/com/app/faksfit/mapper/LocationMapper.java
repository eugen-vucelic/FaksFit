package com.app.faksfit.mapper;

import com.app.faksfit.dto.LocationDTO;
import com.app.faksfit.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public Location toEntity(LocationDTO locationDTO) {
        Location location = new Location();

        location.setLocationName(locationDTO.locationName());
        location.setAddress(locationDTO.address());

        return location;
    }
}
