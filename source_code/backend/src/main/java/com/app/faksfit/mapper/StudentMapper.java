package com.app.faksfit.mapper;

import com.app.faksfit.dto.StudentDTO;
import com.app.faksfit.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "totalPoints", ignore = true)
    @Mapping(target = "passStatus", ignore = true)
    @Mapping(target = "semester", ignore = true )
    @Mapping(target = "academicYear", ignore = true )
    @Mapping(target = "terminList", ignore = true )
    @Mapping(target = "notificationList", ignore = true )
    @Mapping(target = "studentTeacher", ignore = true )
    @Mapping(target = "password", ignore = true )
    @Mapping(target = "dateOfRegistration", ignore = true )
    @Mapping(target = "userFaculty", ignore = true )
    @Mapping(target = "userRole", ignore = true )
    Student toStudentEntity(StudentDTO studentDTO);

    StudentDTO toStudentDTO(Student studentEntity);

    List<StudentDTO> toStudentDtoList(List<Student> StudentEntityList);
}
