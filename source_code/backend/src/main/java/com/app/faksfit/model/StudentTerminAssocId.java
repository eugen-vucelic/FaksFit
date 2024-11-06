package com.app.faksfit.model;

import java.io.Serializable;
import java.util.Objects;

public class StudentTerminAssocId implements Serializable {
    private Long student;
    private Long term;

    public StudentTerminAssocId(Long student, Long term) {
        this.student = student;
        this.term = term;
    }

    public Long getStudent() {
        return student;
    }

    public void setStudent(Long student) {
        this.student = student;
    }

    public Long getTerm() {
        return term;
    }

    public void setTerm(Long term) {
        this.term = term;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentTerminAssocId that)) return false;

        return Objects.equals(getStudent(), that.getStudent()) && Objects.equals(getTerm(), that.getTerm());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getStudent());
        result = 31 * result + Objects.hashCode(getTerm());
        return result;
    }
}
