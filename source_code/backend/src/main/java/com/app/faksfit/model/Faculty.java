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

    @OneToMany(mappedBy = "userFaculty", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<User> users;

    @ManyToOne
    private University facultyUniversity;

    public Faculty(Long facultyId, String facultyName, List<User> users, University facultyUniversity) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.users = users;
        this.facultyUniversity = facultyUniversity;
    }
    public Faculty() {}

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public University getFacultyUniversity() {
        return facultyUniversity;
    }

    public void setFacultyUniversity(University facultyUniversity) {
        this.facultyUniversity = facultyUniversity;
    }
}
