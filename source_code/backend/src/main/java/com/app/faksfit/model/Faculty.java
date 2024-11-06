package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "FAKULTET", schema = "FAKSFIT")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    @Column
    private String facultyName;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    public Faculty(List<User> users, String facultyName, Long facultyId) {
        this.users = users;
        this.facultyName = facultyName;
        this.facultyId = facultyId;
    }

    public Faculty() {
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "facultyName='" + facultyName + '\'' +
                ", facultyId=" + facultyId +
                '}';
    }
}
