package com.example.spring.mapper;

import com.example.spring.domain.Student;
import com.example.spring.dto.student.StudentFullResponse;
import com.example.spring.dto.student.StudentShortResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        uses = {
        EnumMapper.class,
        CityMapper.class,
        SubjectMapper.class
      })
public interface StudentMapper {

    StudentFullResponse mapToFullStudentResponse(Student entity);

    StudentShortResponse mapToShortStudentResponse(Student entity);
}