package com.app.faksfit.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "OBAVIJEST", schema = "FAKSFIT")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column
    private String notificationTitle;

    @Column
    private LocalDateTime notificationDateTime;

    @Column
    private String notificationMessage;

    @ManyToMany(mappedBy = "notificationList")
    private List<Student> studentList;

    public Notification(Long notificationId, String notificationTitle, LocalDateTime notificationDateTime, String notificationMessage, List<Student> studentList) {
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationDateTime = notificationDateTime;
        this.notificationMessage = notificationMessage;
        this.studentList = studentList;
    }

    public Notification() {

    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public LocalDateTime getNotificationDateTime() {
        return notificationDateTime;
    }

    public void setNotificationDateTime(LocalDateTime notificationDateTime) {
        this.notificationDateTime = notificationDateTime;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", notificationTitle='" + notificationTitle + '\'' +
                ", notificationDateTime=" + notificationDateTime +
                ", notificationMessage='" + notificationMessage + '\'' +
                ", studentList=" + studentList +
                '}';
    }
}
