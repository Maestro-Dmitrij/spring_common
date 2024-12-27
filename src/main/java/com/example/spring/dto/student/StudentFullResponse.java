package com.example.spring.dto.student;

import com.example.spring.domain.Subject;
import com.example.spring.dto.city.CityResponse;
import com.example.spring.dto.subject.SubjectResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(name = "StudentShortResponse", description = "Студент")
public class StudentFullResponse extends StudentShortResponse {

    @Schema(description = "Дата и время создания студента", requiredMode = REQUIRED)
    private LocalDateTime creationDate;

    @Schema(description = "Дата и время изменения студента")
    private LocalDateTime modifyDate;

    @Schema(description = "Стипендия/зарплата студента")
    private Double salary;

    @Schema(description = "Повышающий коэффициент для стипендии/зарплаты студента")
    private Double salaryCoefficient;

    @Schema(description = "Учебные предметы студента", requiredMode = REQUIRED)
    private List<SubjectResponse> subjects;
}
