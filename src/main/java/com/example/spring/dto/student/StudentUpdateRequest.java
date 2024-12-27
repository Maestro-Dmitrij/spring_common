package com.example.spring.dto.student;

import com.example.spring.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Getter
@Setter
@Schema(name = "StudentUpdateRequest", description = "Изменение студента")
public class StudentUpdateRequest implements Constants {

    @Schema(description = "Стипендия/зарплата")
    private double salary;

    @Schema(description = "Повышающий коэффициент стипендии/зарплаты")
    @DecimalMin(value = "0", message = "Значение не должно быть меньше " + 0)
    @DecimalMax(value = "1", message = "Значение не должно быть больше " + 1)
    private double salaryCoefficient;

    @Schema(description = "Форма обучения", requiredMode = REQUIRED)
    StudyTypeDto type;

    @Schema(description = "Учебные предметы студента")
    private List<Integer> subjectIds;

    @Schema(description = "Обучается ли студент? По умолчанию false")
    private boolean study;
}
