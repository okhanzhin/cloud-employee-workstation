package com.okhanzhin.employee.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsResponse {

    /**
     * Данные сотрудника.
     */
    private Employee employee;

    /**
     * Закреплённое рабочее место сотрудника.
     */
    private Workstation workstation;
}

