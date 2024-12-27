package com.example.spring.api;

import com.example.spring.dto.student.StudyTypeDto;
import com.example.spring.mapper.EnumMapper;
import com.example.spring.enums.StudyType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/" + StudyType.TYPE)
@Tag(name = StudyType.TYPE, description = "Типы обучения")
@RequiredArgsConstructor
public class StudyTypeRestController {

    private final EnumMapper enumMapper;

    @GetMapping
    @Operation(summary = "Получить список всех типов обучения")
    public ResponseEntity<List<StudyTypeDto>> getAllControlObjectType() {
        return ResponseEntity.ok(enumMapper.studyTypeToListDto(StudyType.values()));
    }
}