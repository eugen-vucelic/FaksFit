package com.app.faksfit.mapper;

import com.app.faksfit.dto.MyPointsDTO;
import com.app.faksfit.dto.MyPointsTermDTO;
import com.app.faksfit.model.Student;
import com.app.faksfit.model.StudentTerminAssoc;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentMyPointsMapper {

    public MyPointsTermDTO mapToMyPointsTermDTO(StudentTerminAssoc studentTerminAssoc) {
        return new MyPointsTermDTO(studentTerminAssoc.getTerm().getTermId(), studentTerminAssoc.getPointsAchieved());
    }

    public MyPointsDTO mapToMyPointsDTO(Student student) {
        String status = String.valueOf(student.getPassStatus());
        int totalPoints = student.getTotalPoints();
        int progress = totalPoints / 100;
        List<StudentTerminAssoc> lista = student.getTerminList();
        List<MyPointsTermDTO> points = lista.stream().map(this::mapToMyPointsTermDTO).toList();
        return new MyPointsDTO(status, totalPoints, progress, points);
    }
}
