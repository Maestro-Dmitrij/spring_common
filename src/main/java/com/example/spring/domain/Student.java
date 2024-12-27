package com.example.spring.domain;

import com.example.spring.enums.StudyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.spring.Constants.NAME_MAX_LENGTH;

@Getter
@NoArgsConstructor
@Table(name = "student")
@Entity
//Serializable для облегчения сериализации в файл
public class Student implements Serializable {

    // Вынесено в константу для наименования контроллера
    public static final String TYPE = "student";

    @Id
    @Generated
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = NAME_MAX_LENGTH, nullable = false)
    private String name;

    @Column(name = "сreation_date")
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "modify_date")
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifyDate;

    @ManyToOne
    @JoinColumn(name = "city_id", foreignKey = @ForeignKey(name = "fk_student__city"), nullable = false)
    private City city;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "salary_coef")
    private Double salaryCoefficient;

    @Column(name = "type", nullable = false, length = 32)
    @Enumerated(EnumType.STRING)
    private StudyType type;

    @Column(name = "study")
    private boolean study;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "student_subject",
            joinColumns = @JoinColumn(name = "student_id",
                    foreignKey = @ForeignKey(name = "fk_student__student")),
            inverseJoinColumns = @JoinColumn(name = "subject_id",
                    foreignKey = @ForeignKey(name = "fk_student__subject")))
    private List<Subject> subjects;

    public Student update(Double salary, Double salaryCoefficient, StudyType type,
                          boolean study, List<Subject> subjects) {
        //this.modifyDate = modifyDate;
        this.salary = salary;
        this.salaryCoefficient = salaryCoefficient;
        this.type = type;
        this.study = study;
        this.subjects = subjects;
        return this;
    }

    public Student(String name, City city, Double salary, Double salaryCoefficient, StudyType type, boolean study,
                   List<Subject> subjects) {
        this.name = name;
        this.city = city;
        this.salary = salary;
        this.salaryCoefficient = salaryCoefficient;
        this.type = type;
        this.study = study;
        this.subjects = subjects;
    }
}