package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "STUDENT", schema = "FAKSFIT")
public class Student extends User {

    @Column(unique = true, nullable = false)
    private String JMBAG;

    @Column
    private Integer totalPoints;

    @Column
    private Boolean passStatus;

    @Column
    private String semester;

    @Column
    private String academicYear;

    @OneToMany(mappedBy = "student") // Many to many veza s extra atributom u vezi
    private List<StudentTerminAssoc> terminList;

    @ManyToMany
    @JoinTable(
            name = "DOBIVA_OBAVIJEST",
            joinColumns = @JoinColumn(name = "STUDENT_ID", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "NOTIFICATION_ID")
    )
    private List<Notification> notificationList = new ArrayList<>();

    @ManyToOne
    private Teacher studentTeacher;

    public Student(Long userId, String firstName, String lastName, String email, String password, String dateOfRegistration, Faculty userFaculty, Role userRole, String JMBAG, Integer totalPoints, Boolean passStatus, String semester, String academicYear, List<StudentTerminAssoc> terminList, List<Notification> notificationList, Teacher studentTeacher) {
        super(userId, firstName, lastName, email, password, dateOfRegistration, userFaculty, userRole);
        this.JMBAG = JMBAG;
        this.totalPoints = totalPoints;
        this.passStatus = passStatus;
        this.semester = semester;
        this.academicYear = academicYear;
        this.terminList = terminList;
        this.notificationList = notificationList;
        this.studentTeacher = studentTeacher;
    }

    public Student(String JMBAG, Integer totalPoints, Boolean passStatus, String semester, String academicYear, List<StudentTerminAssoc> terminList, List<Notification> notificationList, Teacher studentTeacher) {
        this.JMBAG = JMBAG;
        this.totalPoints = totalPoints;
        this.passStatus = passStatus;
        this.semester = semester;
        this.academicYear = academicYear;
        this.terminList = terminList;
        this.notificationList = notificationList;
        this.studentTeacher = studentTeacher;
    }

    public Student() {

    }

    public String getJMBAG() {
        return JMBAG;
    }

    public void setJMBAG(String JMBAG) {
        this.JMBAG = JMBAG;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public Boolean getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(Boolean passStatus) {
        this.passStatus = passStatus;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public List<StudentTerminAssoc> getTerminList() {
        return terminList;
    }

    public void setTerminList(List<StudentTerminAssoc> terminList) {
        this.terminList = terminList;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }

    public Teacher getStudentTeacher() {
        return studentTeacher;
    }

    public void setStudentTeacher(Teacher studentTeacher) {
        this.studentTeacher = studentTeacher;
    }

    @Override
    public String toString() {
        return "Student{" +
                "JMBAG='" + JMBAG + '\'' +
                ", totalPoints=" + totalPoints +
                ", passStatus=" + passStatus +
                ", semester='" + semester + '\'' +
                ", academicYear=" + academicYear +
                ", terminList=" + terminList +
                ", notificationList=" + notificationList +
                ", studentTeacher=" + studentTeacher +
                "} " + super.toString();
    }
}
