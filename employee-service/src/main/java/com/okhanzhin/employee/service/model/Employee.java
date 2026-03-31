package com.okhanzhin.employee.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Модель сотрудника
 * @author okhanzhin on 31.03.2026
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    /**
     * Идентификатор сотрудника
     */
    private Long id;

    /**
     * Имя сотрудника
     */
    private String name;

    /**
     * Фамилия сотрудника
     */
    private String surname;

    /**
     * Дата приема на работу
     */
    private LocalDate dateOfEmployment;
}

