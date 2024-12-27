package com.example.spring.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StudyType {
    FULL_TIME("Очная форма обучения"),
    EVENING("Вечерняя форма обучения"),
    PART_TIME("Заочная форма обучения"),
    OTHER("Другая(сотрудник, дистанционная и т.п.)");

    final private String title;

    public String getName() {
        return name();
    }

    public static final String TYPE = "study-type";
}
