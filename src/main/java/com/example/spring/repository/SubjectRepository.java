package com.example.spring.repository;

import com.example.spring.domain.City;
import com.example.spring.domain.Subject;
import com.example.spring.exception.NotFoundObjectException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer>,
        JpaSpecificationExecutor<City> {

    default Subject getOrThrow(Integer id) {
        if (id == null) {
            throw new NotFoundObjectException("Предмет не найден");
        }
        return findById(id)
                .orElseThrow(() -> new NotFoundObjectException("Предмет c ID = " + id + " не найден"));
    }


    Optional<Subject> findByNameIgnoreCase(String name);
}