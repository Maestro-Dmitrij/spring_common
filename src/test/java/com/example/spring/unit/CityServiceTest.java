package com.example.spring.unit;

import com.example.spring.domain.City;
import com.example.spring.dto.city.CityResponse;
import com.example.spring.dto.city.CitySaveOrUpdateRequest;
import com.example.spring.exception.ExecutionConflictException;
import com.example.spring.mapper.CityMapper;
import com.example.spring.repository.CityRepository;
import com.example.spring.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {

    @InjectMocks
    private  CityService cityService;

    @Mock
    private CityRepository cityRepository;
    @Mock
    private CityMapper cityMapper;

    private City city;

    @BeforeEach
    void setUp(){
        city = new City("Minsk");
        city.setId(1);
    }

    @Test
    void createCityWithValidName() {
        //given
        CitySaveOrUpdateRequest request = new CitySaveOrUpdateRequest();
        request.setName(city.getName());

        //тут используется класс, т.к. у нас при сохранении генерируется id и поэтому мок кидает ошибки
        when(cityRepository.saveAndFlush(isA(City.class))).thenReturn(city);

        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(city.getId());
        cityResponse.setName(city.getName());
        when(cityMapper.map(isA(City.class))).thenReturn(cityResponse);

        //when
        CityResponse response = cityService.create(request);

        //then
        assertNotNull(response);
        assertEquals(cityResponse, response);
        verify(cityRepository, times(1)).saveAndFlush(isA(City.class));
    }

    @Test
    void createCityWithInvalidName() {
        //given
        CitySaveOrUpdateRequest request = new CitySaveOrUpdateRequest();
        request.setName(city.getName());
        when(cityRepository.findByNameIgnoreCase(city.getName())).thenReturn(java.util.Optional.of(city));

        //then
        assertThrows(ExecutionConflictException.class, () -> cityService.create(request));
    }

    @Test
    void updateCityWithValidName() {
        //given
        CitySaveOrUpdateRequest request = new CitySaveOrUpdateRequest();
        request.setName("Mogilev");
        Integer id = city.getId();
        when(cityRepository.getOrThrow(id)).thenReturn(city);
        when(cityRepository.findByNameIgnoreCase(request.getName())).thenReturn(java.util.Optional.empty());

        CityResponse cityResponse = new CityResponse();
        cityResponse.setId(id);
        cityResponse.setName(request.getName());
        when(cityMapper.map(city)).thenReturn(cityResponse);

        //when
        CityResponse response = cityService.update(id, request);

        //then
        assertNotNull(response);
        assertEquals(cityResponse, response);
    }

    @Test
    void updateCityWithInvalidName() {
        //given
        City changeCity = new City();
        changeCity.setId(2);
        changeCity.setName("Vitebsk");

        CitySaveOrUpdateRequest request = new CitySaveOrUpdateRequest();
        request.setName(city.getName());

        when(cityRepository.getOrThrow(changeCity.getId())).thenReturn(changeCity);
        when(cityRepository.findByNameIgnoreCase(request.getName())).thenReturn(java.util.Optional.of(city));

        //then
        assertThrows(ExecutionConflictException.class, () -> cityService.update(changeCity.getId(), request));
    }

    @Test
    void deleteUsingCity() {
        //given
        when(cityRepository.getOrThrow(city.getId())).thenReturn(city);
        doThrow(ExecutionConflictException.class).when(cityRepository).delete(city);

        //then
        assertThrows(ExecutionConflictException.class, () -> cityService.delete(city.getId()));
    }
}
