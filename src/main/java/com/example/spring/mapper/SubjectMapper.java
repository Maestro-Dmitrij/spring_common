package com.example.spring.mapper;

import com.example.spring.domain.Subject;
import com.example.spring.dto.subject.SubjectResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface SubjectMapper {

    SubjectResponse map(Subject entity);
}