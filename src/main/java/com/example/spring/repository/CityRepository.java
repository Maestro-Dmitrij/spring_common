package com.example.spring.repository;

import com.example.spring.domain.City;
import com.example.spring.exception.NotFoundObjectException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer>,
        JpaSpecificationExecutor<City> {

    default City getOrThrow(Integer id) {
        if (id == null) {
            throw new NotFoundObjectException("Город не найден");
        }
        return findById(id)
                .orElseThrow(() -> new NotFoundObjectException("Город c ID = " + id + " не найден"));
    }


    Optional<City> findByNameIgnoreCase(String name);
}