package com.app.faksfit.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TERMIN", schema = "FAKSFIT")
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long termId;

    @Column
    private String termDescription;
    @Column
    private LocalDateTime termStart;
    @Column
    private LocalDateTime termEnd;
    @Column
    private Integer maxPoints;
    @Column
    private Integer capacity;

    @OneToOne
    private ActivityType activityType;

    @ManyToMany(mappedBy = termList)
    private List<Student> students;

    public Term(Long termId, String termDescription, LocalDateTime termStart, LocalDateTime termEnd, Integer maxPoints, Integer capacity, ActivityType activityType, List<Student> students) {
        this.termId = termId;
        this.termDescription = termDescription;
        this.termStart = termStart;
        this.termEnd = termEnd;
        this.maxPoints = maxPoints;
        this.capacity = capacity;
        this.activityType = activityType;
        this.students = students;
    }

    public Term() {

    }

    public Long getTermId() {
        return termId;
    }

    public void setTermId(Long termId) {
        this.termId = termId;
    }

    public String getTermDescription() {
        return termDescription;
    }

    public void setTermDescription(String termDescription) {
        this.termDescription = termDescription;
    }

    public LocalDateTime getTermStart() {
        return termStart;
    }

    public void setTermStart(LocalDateTime termStart) {
        this.termStart = termStart;
    }

    public LocalDateTime getTermEnd() {
        return termEnd;
    }

    public void setTermEnd(LocalDateTime termEnd) {
        this.termEnd = termEnd;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Term{" +
                "activityType=" + activityType +
                ", capacity=" + capacity +
                ", maxPoints=" + maxPoints +
                ", termEnd=" + termEnd +
                ", termStart=" + termStart +
                ", termDescription='" + termDescription + '\'' +
                ", termId=" + termId +
                '}';
    }
}
