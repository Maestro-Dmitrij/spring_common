package com.example.spring.api;

import com.example.spring.domain.City;
import com.example.spring.dto.city.CityResponse;
import com.example.spring.dto.city.CitySaveOrUpdateRequest;
import com.example.spring.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + City.TYPE)
@Tag(name = City.TYPE, description = "Город")
@RequiredArgsConstructor
public class CityRestController {

    private final CityService cityService;

    @PostMapping()
    @Operation(summary = "Создание города")
    public ResponseEntity<CityResponse> createCity(
            @Parameter(description = "DTO объекта")
            @RequestBody CitySaveOrUpdateRequest saveRequest) {
        CityResponse dd = cityService.create(saveRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(dd);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактировать город по ID")
    public ResponseEntity<CityResponse> updateCity(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id,
            @Parameter(description = "DTO объекта") @RequestBody CitySaveOrUpdateRequest saveRequest) {
        return ResponseEntity.ok(cityService.update(id, saveRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление города")
    public ResponseEntity<Void> deleteHardCityById(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id) {
        cityService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Просмотр справочника городов")
    public ResponseEntity<Page<CityResponse>> getAllCities(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(cityService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить город по ID")
    public ResponseEntity<CityResponse> getCityById(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id) {
        return ResponseEntity.ok(cityService.get(id));
    }
}
