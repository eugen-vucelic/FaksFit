package com.app.faksfit.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "userId")
@Table(name = "ACTIVITY_LEADER", schema = "FAKSFIT")
public class ActivityLeader extends User{

    @Column
    private String profilePictureURL;

    @OneToMany(mappedBy = "activityLeaderTerm", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Term> activityLeaderTerms;

    @ManyToOne
    private ActivityType leaderActivityType;


    public ActivityLeader(Long userId, String firstName, String lastName, String email, String password, String dateOfRegistration, Faculty userFaculty, Role userRole, String profilePictureURL, List<Term> activityLeaderTerms, ActivityType leaderActivityType) {
        super(userId, firstName, lastName, email, password, dateOfRegistration, userFaculty, userRole);
        this.profilePictureURL = profilePictureURL;
        this.activityLeaderTerms = activityLeaderTerms;
        this.leaderActivityType = leaderActivityType;
    }

    public ActivityLeader(String profilePictureURL, List<Term> activityLeaderTerms, ActivityType leaderActivityType) {
        this.profilePictureURL = profilePictureURL;
        this.activityLeaderTerms = activityLeaderTerms;
        this.leaderActivityType = leaderActivityType;
    }

    public ActivityLeader() {

    }

    public String getProfilePictureURL() {
        return profilePictureURL;
    }

    public void setProfilePictureURL(String profilePictureURL) {
        this.profilePictureURL = profilePictureURL;
    }

    public List<Term> getActivityLeaderTerms() {
        return activityLeaderTerms;
    }

    public void setActivityLeaderTerms(List<Term> activityLeaderTerms) {
        this.activityLeaderTerms = activityLeaderTerms;
    }

    public ActivityType getLeaderActivityType() {
        return leaderActivityType;
    }

    public void setLeaderActivityType(ActivityType leaderActivityType) {
        this.leaderActivityType = leaderActivityType;
    }

    @Override
    public String toString() {
        return "ActivityLeader{" +
                "profilePictureURL='" + profilePictureURL + '\'' +
                ", activityLeaderTerms=" + activityLeaderTerms +
                ", leaderActivityType=" + leaderActivityType +
                "} " + super.toString();
    }
}
