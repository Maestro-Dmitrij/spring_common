package com.example.spring.dto.subject;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(name = "SubjectResponse", description = "Учебный предмет")
public class SubjectResponse {

    @Schema(description = "ID", requiredMode = REQUIRED)
    private Integer id;

    @Schema(description = "Наименование", requiredMode = REQUIRED)
    private String name;
}
