package com.example.spring.dto.student;

import com.example.spring.dto.subject.SubjectWithGradeResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(name = "StudentShortResponse", description = "Студент")
public class StudentShortWithGradeResponse extends StudentShortResponse {

    @Schema(description = "Сданные предметы студента", requiredMode = REQUIRED)
    private List<SubjectWithGradeResponse> subjects;
}
