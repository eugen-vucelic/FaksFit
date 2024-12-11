package com.app.faksfit.model;

import jakarta.persistence.*;

@Entity
@Table(name = "PRIJAVLJUJE_TERMIN")
@IdClass(StudentTerminAssocId.class)
public class StudentTerminAssoc {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "userId")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "termin_id", referencedColumnName = "termId")
    private Term term;

    @Column(name = "ostvareni_bodovi")
    private Integer pointsAchieved;

    public StudentTerminAssoc(Student student, Term term, Integer pointsAchieved) {
        this.student = student;
        this.term = term;
        this.pointsAchieved = pointsAchieved;
    }

    public StudentTerminAssoc() {}

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Term getTerm() {
        return term;
    }

    public void setTerm(Term term) {
        this.term = term;
    }

    public Integer getPointsAchieved() {
        return pointsAchieved;
    }

    public void setPointsAchieved(Integer pointsAchieved) {
        this.pointsAchieved = pointsAchieved;
    }
}
