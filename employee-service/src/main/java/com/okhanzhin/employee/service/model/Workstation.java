package com.okhanzhin.employee.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workstation {

    /**
     * Идентификатор рабочего места
     */
    private Long id;

    /**
     * Название рабочего места
     */
    private String name;

    /**
     * Операционная система рабочего места
     */
    private String os;
}

