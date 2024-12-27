package com.example.spring.api;

import com.example.spring.domain.Subject;
import com.example.spring.dto.subject.SubjectResponse;
import com.example.spring.dto.subject.SubjectSaveOrUpdateRequest;
import com.example.spring.service.SubjectService;
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
@RequestMapping("/" + Subject.TYPE)
@Tag(name = Subject.TYPE, description = "Учебный предмет")
@RequiredArgsConstructor
public class SubjectRestController {

    private final SubjectService subjectService;

    @PostMapping()
    @Operation(summary = "Создание учебного предмета")
    public ResponseEntity<SubjectResponse> createSubject(
            @Parameter(description = "DTO объекта")
            @RequestBody SubjectSaveOrUpdateRequest saveRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.create(saveRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактировать учебный предмет по ID")
    public ResponseEntity<SubjectResponse> updateSubject(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id,
            @Parameter(description = "DTO объекта") @RequestBody SubjectSaveOrUpdateRequest saveRequest) {
        return ResponseEntity.ok(subjectService.update(id, saveRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление учебного предмета")
    public ResponseEntity<Void> deleteHardSubjectById(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id) {
        subjectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Просмотр справочника учебных предметов")
    public ResponseEntity<Page<SubjectResponse>> getAllSubjects(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(subjectService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить учебный предмет по ID")
    public ResponseEntity<SubjectResponse> getSubjectById(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id) {
        return ResponseEntity.ok(subjectService.get(id));
    }
}
