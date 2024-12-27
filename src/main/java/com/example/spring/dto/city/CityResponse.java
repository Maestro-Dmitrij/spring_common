package com.example.spring.dto.city;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(name = "CityResponse", description = "Город")
public class CityResponse {

    @Schema(description = "ID", requiredMode = REQUIRED)
    private Integer id;

    @Schema(description = "Наименование", requiredMode = REQUIRED)
    private String name;
}
