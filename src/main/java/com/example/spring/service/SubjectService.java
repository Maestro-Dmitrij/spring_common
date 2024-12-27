package com.example.spring.service;

import com.example.spring.domain.City;
import com.example.spring.domain.Subject;
import com.example.spring.dto.city.CityResponse;
import com.example.spring.dto.city.CitySaveOrUpdateRequest;
import com.example.spring.dto.subject.SubjectResponse;
import com.example.spring.dto.subject.SubjectSaveOrUpdateRequest;
import com.example.spring.exception.ExecutionConflictException;
import com.example.spring.mapper.CityMapper;
import com.example.spring.mapper.SubjectMapper;
import com.example.spring.repository.CityRepository;
import com.example.spring.repository.SubjectRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Transactional
    public SubjectResponse create(@Valid SubjectSaveOrUpdateRequest saveRequest) {
        checkUniqueName(saveRequest.getName(), null);
        Subject subject = new Subject(saveRequest.getName());
        subjectRepository.saveAndFlush(subject);
        return subjectMapper.map(subject);
    }

    @Transactional
    public SubjectResponse update(Integer id, @Valid SubjectSaveOrUpdateRequest saveRequest) {
        Subject subject = subjectRepository.getOrThrow(id);
        checkUniqueName(saveRequest.getName(), id);
        subject.setName(saveRequest.getName());
        return subjectMapper.map(subject);
    }

    /**
     * Удаление предмета по ID c проверкой на связь со студентами
     *
     * @param id - ID предмета
     */
    public void delete(Integer id) {
        Subject subject = subjectRepository.getOrThrow(id);
        try {
            subjectRepository.delete(subject);
        } catch (Exception e) {
            throw new ExecutionConflictException(String.format("Предмет '%s' не может быть удален так как в системе есть студент(ы) из данного города",
                    subject.getName()));
        }
    }

    /**
     * Поиск предмета по ID
     *
     * @param id - ID предмета
     * @return @link {SubjectResponse}
     */
    public SubjectResponse get(Integer id) {
        return subjectMapper.map(subjectRepository.getOrThrow(id));
    }

    /**
     * Просмотр справочника учебных предметов
     *
     * @param pageable - пагинация {@link Pageable}
     * @return - @link {Page<SubjectResponse>}
     */
    public Page<SubjectResponse> getAll(Pageable pageable) {
        return subjectRepository.findAll(pageable).map(subjectMapper::map);
    }

    /**
     * Проверка наличия предмета с аналогичным наименованием
     *
     * @param name - наименование предмета
     * @param id - id предмета
     */
    private void checkUniqueName(String name, Integer id){
        Optional<Subject> similarSubject = subjectRepository.findByNameIgnoreCase(name);
        String message = String.format("Наименование учебного предмета '%s' уже существует", name);
        if (id != null && similarSubject.isPresent() && similarSubject.get().getId() != id){
            throw new ExecutionConflictException(message);
        }
        if (id == null && similarSubject.isPresent()){
            throw new ExecutionConflictException(message);
        }
    }
}
