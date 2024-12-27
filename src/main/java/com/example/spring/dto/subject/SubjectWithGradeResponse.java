package com.example.spring.dto.subject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(name = "SubjectWithGradeResponse", description = "Учебный предмет с отметкой")
public class SubjectWithGradeResponse {

    @Schema(description = "ID", requiredMode = REQUIRED)
    private Integer id;

    @Schema(description = "Наименование", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Отметка", requiredMode = REQUIRED)
    private Double grade;
}
