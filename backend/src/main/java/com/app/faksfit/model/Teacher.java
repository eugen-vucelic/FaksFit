package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "TEACHER")
public class Teacher extends User{

    @Column
    private String profilePictureURL;

    @Column
    private String officeLocation;

    @OneToMany(mappedBy = "studentTeacher")
    private List<Student> students;


    public Teacher(Long userId, String firstName, String lastName, String email, String password, String dateOfRegistration, Faculty userFaculty, Role userRole, String profilePictureURL, String officeLocation, List<Student> students) {
        super(userId, firstName, lastName, email, password, dateOfRegistration, userFaculty, userRole);
        this.profilePictureURL = profilePictureURL;
        this.officeLocation = officeLocation;
        this.students = students;
    }

    public Teacher(String profilePictureURL, String officeLocation, List<Student> students) {
        this.profilePictureURL = profilePictureURL;
        this.officeLocation = officeLocation;
        this.students = students;
    }

    public Teacher() {

    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public String getOfficeLocation() {
        return officeLocation;
    }

    public void setOfficeLocation(String officeLocation) {
        this.officeLocation = officeLocation;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "profilePictureURL='" + profilePictureURL + '\'' +
                ", officeLocation='" + officeLocation + '\'' +
                ", students=" + students +
                "} " + super.toString();
    }
}
