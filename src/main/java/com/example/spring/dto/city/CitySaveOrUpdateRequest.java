package com.example.spring.dto.city;

import com.example.spring.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;


@Getter
@Setter
@Schema(name = "CitySaveAndUpdateRequest", description = "Создание/обновление города")
public class CitySaveOrUpdateRequest implements Constants {

    @Schema(description = NAME, requiredMode = REQUIRED)
    @Size(max = NAME_MAX_LENGTH, message = "Наименование города должно содержать не более " + NAME_MAX_LENGTH + " символов")
    @NotBlank(message = NAME_SHOULD_NOT_BE_EMPTY)
    private String name;
}
