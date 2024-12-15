package com.app.faksfit.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "FAKULTET")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facultyId;

    @Column
    private String facultyName;

    @OneToMany(mappedBy = "userFaculty", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Student> studenti;

    @ManyToOne
    private University facultyUniversity;

    public Faculty(Long facultyId, String facultyName, List<User> users, University facultyUniversity) {
        this.facultyId = facultyId;
        this.facultyName = facultyName;
        this.studenti = studenti;
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

    @JsonManagedReference
    public List<Student> getUsers() {
        return studenti;
    }

    public void setUsers(List<Student> studenti) {
        this.studenti = studenti;
    }

    public University getFacultyUniversity() {
        return facultyUniversity;
    }

    public void setFacultyUniversity(University facultyUniversity) {
        this.facultyUniversity = facultyUniversity;
    }
}
