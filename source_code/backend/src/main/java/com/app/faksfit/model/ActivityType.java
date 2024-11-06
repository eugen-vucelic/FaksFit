package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TIP_AKTIVNOSTI", schema = "FAKSFIT")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityTypeId;

    @Column
    private String activityTypeName;

    @OneToMany(mappedBy = "leaderActivityType", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ActivityLeader> activityTypeLeaders;

    @OneToMany(mappedBy = "activityTypeTerm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Term> activityTypeTerms;

    public ActivityType(Long activityTypeId, String activityTypeName, List<ActivityLeader> activityTypeLeaders, List<Term> activityTypeTerms) {
        this.activityTypeId = activityTypeId;
        this.activityTypeName = activityTypeName;
        this.activityTypeLeaders = activityTypeLeaders;
        this.activityTypeTerms = activityTypeTerms;
    }
    public ActivityType() {}

    public Long getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Long activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public List<ActivityLeader> getActivityTypeLeaders() {
        return activityTypeLeaders;
    }

    public void setActivityTypeLeaders(List<ActivityLeader> activityTypeLeaders) {
        this.activityTypeLeaders = activityTypeLeaders;
    }

    public List<Term> getActivityTypeTerms() {
        return activityTypeTerms;
    }

    public void setActivityTypeTerms(List<Term> activityTypeTerms) {
        this.activityTypeTerms = activityTypeTerms;
    }

    @Override
    public String toString() {
        return "ActivityType{" +
                "activityTypeId=" + activityTypeId +
                ", activityTypeName='" + activityTypeName + '\'' +
                ", activityTypeLeaders=" + activityTypeLeaders +
                ", activityTypeTerms=" + activityTypeTerms +
                '}';
    }
}

