package com.example.spring.dto.student;

import com.example.spring.dto.city.CityResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(name = "StudentShortResponse", description = "Студент (краткая информация)")
public class StudentShortResponse {

    @Schema(description = "ID", requiredMode = REQUIRED)
    private Integer id;

    @Schema(description = "Имя студента", requiredMode = REQUIRED)
    private String name;

    @Schema(description = "Город проживания студента", requiredMode = REQUIRED)
    private CityResponse city;

    @Schema(description = "Форма обучения студента", requiredMode = REQUIRED)
    private StudyTypeDto type;

    @Schema(description = "Учится ли студент?", requiredMode = REQUIRED)
    private boolean study;
}
