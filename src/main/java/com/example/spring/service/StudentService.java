package com.example.spring.service;

import com.example.spring.domain.City;
import com.example.spring.domain.Student;
import com.example.spring.domain.Subject;
import com.example.spring.dto.student.StudentFullResponse;
import com.example.spring.dto.student.StudentSaveRequest;
import com.example.spring.dto.student.StudentShortResponse;
import com.example.spring.dto.student.StudentUpdateRequest;
import com.example.spring.exception.ExecutionConflictException;
import com.example.spring.mapper.StudentMapper;
import com.example.spring.repository.CityRepository;
import com.example.spring.repository.StudentRepository;
import com.example.spring.repository.SubjectRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class StudentService {

    private final CityRepository cityRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Transactional
    public StudentFullResponse create(@Valid StudentSaveRequest saveRequest) {
        checkUniqueName(saveRequest.getName());
        City city = cityRepository.getOrThrow(saveRequest.getCityId());
        List<Subject> subjects = saveRequest.getSubjectIds().stream().map(subjectRepository::getOrThrow).toList();
        Student student = new Student(saveRequest.getName(), city, saveRequest.getSalary(), saveRequest.getSalaryCoefficient(),
                saveRequest.getType().getName(), saveRequest.isStudy(), subjects);
        studentRepository.saveAndFlush(student);
        return studentMapper.mapToFullStudentResponse(student);
    }

    @Transactional
    public StudentFullResponse update(Integer id, @Valid StudentUpdateRequest saveRequest) {
        Student student = studentRepository.getOrThrow(id);
        List<Subject> subjects = saveRequest.getSubjectIds().stream().map(subjectRepository::getOrThrow).toList();
        student.update(saveRequest.getSalary(), saveRequest.getSalaryCoefficient(),
                saveRequest.getType().getName(), saveRequest.isStudy(), subjects);
        return studentMapper.mapToFullStudentResponse(student);
    }

    /**
     * Удаление студента по ID
     *
     * @param id - ID студента
     */
    public void delete(Integer id) {
        studentRepository.deleteById(id);
    }

    /**
     * Поиск студента по ID
     *
     * @param id - ID студента
     * @return @link {StudentFullResponse}
     */
    public StudentFullResponse get(Integer id) {
        return studentMapper.mapToFullStudentResponse(studentRepository.getOrThrow(id));
    }

    /**
     * Просмотр списка студентов
     *
     * @param pageable - пагинация {@link Pageable}
     * @return - @link {Page<StudentShortResponse>}
     */
    public Page<StudentShortResponse> getAll(Pageable pageable) {
        return studentRepository.findAll(pageable).map(studentMapper::mapToShortStudentResponse);
    }

    /**
     * Проверка наличия студента с аналогичным именем
     *
     * @param name - имя студента
     */
    private void checkUniqueName(String name) {
        if (studentRepository.findByNameIgnoreCase(name).isPresent()) {
            throw new ExecutionConflictException(String.format("Студент с именем '%s' уже существует", name));
        }
    }
}
