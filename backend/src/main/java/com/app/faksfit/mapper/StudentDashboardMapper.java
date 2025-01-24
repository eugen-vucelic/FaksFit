package com.app.faksfit.mapper;

import com.app.faksfit.dto.*;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import com.app.faksfit.model.Teacher;
import com.app.faksfit.model.Location;
import com.app.faksfit.model.ActivityType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class StudentDashboardMapper {

    public StudentDashboardDTO toStudentDashboardDTO(Student student) {
        if (student == null) {
            return null;
        }

        List<TermDTO> terminList = mapTerminList(student.getTerminList());
        TeacherDTO teacherDTO = toTeacherDTO(student.getStudentTeacher());

        return new StudentDashboardDTO(
                student.getFirstName(),
                student.getLastName(),
                student.getJMBAG(),
                student.getPassStatus(),
                terminList,
                teacherDTO,
                student.getTotalPoints()
        );
    }


    public TermDTO map(StudentTerminAssoc studentTerminAssoc) {
        if (studentTerminAssoc == null || studentTerminAssoc.getTerm() == null) {
            return null;
        }

        return new TermDTO(
                studentTerminAssoc.getTerm().getMaxPoints(),
                studentTerminAssoc.getTerm().getTermStart(),
                studentTerminAssoc.getTerm().getTermEnd(),
                mapLocation(studentTerminAssoc.getTerm().getLocationTerm()),
                mapActivityType(studentTerminAssoc.getTerm().getActivityTypeTerm()),
                studentTerminAssoc.getTerm().getCapacity(),
                studentTerminAssoc.getTerm().getTermId()
        );
    }


    public TeacherDTO toTeacherDTO(Teacher teacher) {
        if (teacher == null) {
            return null;
        }

        return new TeacherDTO(
                teacher.getFirstName(),
                teacher.getLastName(),
                teacher.getEmail(),
                teacher.getOfficeLocation()
        );
    }


    public List<TermDTO> mapTerminList(List<StudentTerminAssoc> terminList) {
        if (terminList == null) {
            return List.of();
        }

        return terminList.stream()
                .filter(Objects::nonNull)
                .map(this::map)
                .collect(Collectors.toList());
    }


    public LocationDTO mapLocation(Location location) {
        if (location == null) {
            return null;
        }

        return new LocationDTO(
                location.getLocationName(),
                location.getAddress()
        );
    }


    public ActivityTypeDTO mapActivityType(ActivityType activityType) {
        if (activityType == null) {
            return null;
        }

        return new ActivityTypeDTO(
                activityType.getActivityTypeName()
        );
    }
}
