package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "LOKACIJA", schema = "FAKSFIT")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column
    private String locationName;
    @Column
    private String address;
    @Column
    private String cityCode;

    @OneToMany(mappedBy = "locationTerm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Term> termsOnLocation;

    public Location(Long locationId, String locationName, String address, String cityCode, List<Term> termsOnLocation) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.address = address;
        this.cityCode = cityCode;
        this.termsOnLocation = termsOnLocation;
    }

    public Location() {

    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<Term> gettermsOnLocation() {
        return termsOnLocation;
    }

    public void settermsOnLocation(List<Term> termsOnLocation) {
        this.termsOnLocation = termsOnLocation;
    }

    @Override
    public String toString() {
        return "Location{" +
                "cityCode='" + cityCode + '\'' +
                ", address='" + address + '\'' +
                ", locationName='" + locationName + '\'' +
                ", locationId=" + locationId +
                '}';
    }
}
