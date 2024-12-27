package com.example.spring.dto.student;

import com.example.spring.enums.StudyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudyTypeDto {

    @Enumerated(EnumType.STRING)
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private StudyType name;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String title;
    }
