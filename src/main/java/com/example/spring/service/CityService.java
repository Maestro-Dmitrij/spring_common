package com.example.spring.service;

import com.example.spring.domain.City;
import com.example.spring.dto.city.CityResponse;
import com.example.spring.dto.city.CitySaveOrUpdateRequest;
import com.example.spring.exception.ExecutionConflictException;
import com.example.spring.mapper.CityMapper;
import com.example.spring.repository.CityRepository;
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
public class CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Transactional
    public CityResponse create(@Valid CitySaveOrUpdateRequest saveRequest) {
        checkUniqueName(saveRequest.getName(), null);
        City city = new City(saveRequest.getName());
        cityRepository.saveAndFlush(city);
        return cityMapper.map(city);
    }

    @Transactional
    public CityResponse update(Integer id, @Valid CitySaveOrUpdateRequest saveRequest) {
        City city = cityRepository.getOrThrow(id);
        checkUniqueName(saveRequest.getName(), id);
        city.setName(saveRequest.getName());
        return cityMapper.map(city);
    }

    /**
     * Удаление города по ID c проверкой на связь со студентами
     *
     * @param id - ID города
     */
    public void delete(Integer id) {
        City city = cityRepository.getOrThrow(id);
        try {
            cityRepository.delete(city);
        } catch (Exception e) {
            throw new ExecutionConflictException(String.format("Город '%s' не может быть удален так как в системе есть студент(ы) из данного города",
                    city.getName()));
        }
    }

    /**
     * Поиск города по ID
     *
     * @param id - ID города
     * @return @link {CityResponse}
     */
    public CityResponse get(Integer id) {
        return cityMapper.map(cityRepository.getOrThrow(id));
    }

    /**
     * Просмотр справочника городов
     *
     * @param pageable - пагинация {@link Pageable}
     * @return - @link {Page<CityResponse>}
     */
    public Page<CityResponse> getAll(Pageable pageable) {
        return cityRepository.findAll(pageable).map(cityMapper::map);
    }

    /**
     * Проверка наличия города с аналогичным наименованием
     *
     * @param name - наименование города
     * @param id - id города
     */
    private void checkUniqueName(String name, Integer id){
        Optional<City> similarCity = cityRepository.findByNameIgnoreCase(name);
        String message = String.format("Наименование города '%s' уже существует", name);
        if (id != null && similarCity.isPresent() && similarCity.get().getId() != id){
            throw new ExecutionConflictException(message);
        }
        if (id == null && similarCity.isPresent()){
            throw new ExecutionConflictException(message);
        }
    }
}
