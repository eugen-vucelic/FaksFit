package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "SVEUCILISTE", schema = "FAKSFIT")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long universityId;

    @Column
    private String universityName;

    @OneToMany(mappedBy = "facultyId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Faculty> faculties;

    public University(Long universityId, String universityName, List<Faculty> faculties) {
        this.universityId = universityId;
        this.universityName = universityName;
        this.faculties = faculties;
    }

    public University() {

    }

    @Override
    public String toString() {
        return "University{" +
                "universityId=" + universityId +
                ", universityName='" + universityName + '\'' +
                '}';
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public Long getUniversityId() {
        return universityId;
    }
}
