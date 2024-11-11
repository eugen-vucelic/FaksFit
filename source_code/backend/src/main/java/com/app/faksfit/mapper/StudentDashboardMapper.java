package com.app.faksfit.mapper;

import com.app.faksfit.dto.StudentDashboardDTO;
import com.app.faksfit.dto.TeacherDTO;
import com.app.faksfit.dto.TermDTO;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import com.app.faksfit.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentDashboardMapper {

    @Mapping(target = "JMBAG", source = "JMBAG")
    @Mapping(target = "passStatus", source = "passStatus")
    @Mapping(target = "totalPoints", source = "totalPoints")
    @Mapping(target = "terminList", source = "terminList")
    @Mapping(target = "teacher", source = "studentTeacher")
    StudentDashboardDTO toStudentDashboardDTO(Student student);

    @Mapping(target = "maxPoints", source = "term.maxPoints")
    @Mapping(target = "termStart", source = "term.termStart")
    @Mapping(target = "termEnd", source = "term.termEnd")
    @Mapping(target = "location", source = "term.locationTerm")
    @Mapping(target = "activityType", source = "term.activityTypeTerm")
    TermDTO map(StudentTerminAssoc studentTerminAssoc);

    TeacherDTO toTeacherDTO(Teacher teacher);

    List<TermDTO> mapTerminList(List<StudentTerminAssoc> terminList);

}
