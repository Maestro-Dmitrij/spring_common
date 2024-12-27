package com.example.spring.api;

import com.example.spring.domain.Student;
import com.example.spring.dto.student.StudentFullResponse;
import com.example.spring.dto.student.StudentSaveRequest;
import com.example.spring.dto.student.StudentShortResponse;
import com.example.spring.dto.student.StudentUpdateRequest;
import com.example.spring.service.StudentService;
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
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/" + Student.TYPE)
@Tag(name = Student.TYPE, description = "Студенты")
@RequiredArgsConstructor
public class StudentRestController {

    private final StudentService studentService;

    @GetMapping
    @Operation(summary = "Получить всех студентов")
    public ResponseEntity<Page<StudentShortResponse>> getAllStudents(
            @ParameterObject Pageable pageable
/*            @Parameter(description = "Строка текстового поиска Осуществляется поиск по полям: имя, фамилия, отчество)")
            @RequestParam(value = "searchString", required = false) String searchString*/
            ) {
        return ResponseEntity.ok(studentService.getAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить студента по ID")
    public ResponseEntity<StudentFullResponse> getStudentById(@Parameter(description = "ID", required = true)
                                                                  @PathVariable("id") Integer id) {
        return ResponseEntity.ok(studentService.get(id));
    }

    @PostMapping
    @Operation(summary = "Создать студента")
    public ResponseEntity<StudentFullResponse> createStudent(
            @Parameter(description = "DTO объекта") @RequestBody StudentSaveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Редактировать студента")
    public ResponseEntity<StudentFullResponse> updateStudentById(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id,
            @Parameter(description = "DTO объекта") @RequestBody StudentUpdateRequest request) {
        return ResponseEntity.ok(studentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление студента")
    public ResponseEntity<Void> deleteStudentById(
            @Parameter(description = "ID", required = true) @PathVariable("id") Integer id) {
        studentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}