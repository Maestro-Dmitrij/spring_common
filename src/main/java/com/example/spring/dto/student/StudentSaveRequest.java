package com.example.spring.dto.student;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Getter
@Setter
@Schema(name = "StudentSaveRequest", description = "Создание студента")
public class StudentSaveRequest extends StudentUpdateRequest {

    @Schema(description = "Имя студента", requiredMode = REQUIRED)
    @Size(max = NAME_MAX_LENGTH, message = "Имя студента должно содержать не более " + NAME_MAX_LENGTH + " символов")
    @NotBlank(message = "Имя студента не может быть пустым")
    private String name;

    @Schema(description = "ID города проживания студента", requiredMode = REQUIRED)
    private int cityId;
}
