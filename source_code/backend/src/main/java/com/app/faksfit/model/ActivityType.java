package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "TIPAKTIVNOSTI", schema = "FAKSFIT")
public class ActivityType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityTypeId;

    @Column
    private String activityTypeName;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ActivityLeader> activityLeaders;

    public ActivityType(Long activityTypeId, String activityTypeName, List<ActivityLeader> activityLeaders) {
        this.activityTypeId = activityTypeId;
        this.activityTypeName = activityTypeName;
        this.activityLeaders = activityLeaders;
    }

    public ActivityType() {
    }

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

    public List<ActivityLeader> getActivityLeaders() {
        return activityLeaders;
    }

    public void setActivityLeaders(List<ActivityLeader> activityLeaders) {
        this.activityLeaders = activityLeaders;
    }

    @Override
    public String toString() {
        return "ActivityType{" +
                "activityTypeId=" + activityTypeId +
                ", activityTypeName='" + activityTypeName + '\'' +
                '}';
    }
}
