package com.example.spring.mapper;

import com.example.spring.domain.City;
import com.example.spring.dto.city.CityResponse;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CityMapper {

    CityResponse map(City entity);
}