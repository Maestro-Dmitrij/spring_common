package com.example.spring.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;

import java.io.Serializable;

import static com.example.spring.Constants.NAME_MAX_LENGTH;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "city")
@Entity
public class City implements Serializable {

    public static final String TYPE = "city";

    @Id
    @Generated
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = NAME_MAX_LENGTH, nullable = false)
    private String name;

    public City(String name) {
        this.name = name;
    }
}