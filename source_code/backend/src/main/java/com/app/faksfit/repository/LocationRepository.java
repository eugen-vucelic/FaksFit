package com.app.faksfit.repository;

import com.app.faksfit.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByLocationId(Long locationId);

    Location findByLocationName(String locationName);

    List<Location> findByCityCode(String cityCode);

    boolean existsByLocationName(String locationName);
}
