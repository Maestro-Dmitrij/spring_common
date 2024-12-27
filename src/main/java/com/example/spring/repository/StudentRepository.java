package com.example.spring.repository;

import com.example.spring.domain.City;
import com.example.spring.domain.Student;
import com.example.spring.exception.NotFoundObjectException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer>,
        JpaSpecificationExecutor<City> {

    default Student getOrThrow(Integer id) {
        if (id == null) {
            throw new NotFoundObjectException("Студент не найден");
        }
        return findById(id)
                .orElseThrow(() -> new NotFoundObjectException("Студент c ID = " + id + " не найден"));
    }

    Optional<Student> findByNameIgnoreCase(String name);
}