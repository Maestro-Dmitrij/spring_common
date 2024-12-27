package com.example.spring.mapper;

import com.example.spring.dto.student.StudyTypeDto;
import com.example.spring.enums.StudyType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring")
public interface EnumMapper {
    @Mapping(target = "name", source = "entity")
    StudyTypeDto studyTypeToDto(StudyType entity);

    List<StudyTypeDto> studyTypeToListDto(StudyType[] entities);
}
