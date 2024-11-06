package com.app.faksfit.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    private ActivityType activityTypeTerm;

    @ManyToOne
    private ActivityLeader activityLeaderTerm;

    @ManyToOne
    private Location locationTerm;

    @OneToMany(mappedBy = "term")
    private List<StudentTerminAssoc> studentList;

    public Term(Long termId, String termDescription, LocalDateTime termStart, LocalDateTime termEnd, Integer maxPoints, Integer capacity, ActivityType activityTypeTerm, ActivityLeader activityLeaderTerm, Location locationTerm, List<StudentTerminAssoc> studentList) {
        this.termId = termId;
        this.termDescription = termDescription;
        this.termStart = termStart;
        this.termEnd = termEnd;
        this.maxPoints = maxPoints;
        this.capacity = capacity;
        this.activityTypeTerm = activityTypeTerm;
        this.activityLeaderTerm = activityLeaderTerm;
        this.locationTerm = locationTerm;
        this.studentList = studentList;
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

    public ActivityType getActivityTypeTerm() {
        return activityTypeTerm;
    }

    public void setActivityTypeTerm(ActivityType activityTypeTerm) {
        this.activityTypeTerm = activityTypeTerm;
    }

    public ActivityLeader getActivityLeaderTerm() {
        return activityLeaderTerm;
    }

    public void setActivityLeaderTerm(ActivityLeader activityLeaderTerm) {
        this.activityLeaderTerm = activityLeaderTerm;
    }

    public Location getLocationTerm() {
        return locationTerm;
    }

    public void setLocationTerm(Location locationTerm) {
        this.locationTerm = locationTerm;
    }

    public List<StudentTerminAssoc> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentTerminAssoc> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "Term{" +
                "termId=" + termId +
                ", termDescription='" + termDescription + '\'' +
                ", termStart=" + termStart +
                ", termEnd=" + termEnd +
                ", maxPoints=" + maxPoints +
                ", capacity=" + capacity +
                ", activityTypeTerm=" + activityTypeTerm +
                ", activityLeaderTerm=" + activityLeaderTerm +
                ", locationTerm=" + locationTerm +
                ", studentList=" + studentList +
                '}';
    }
}
