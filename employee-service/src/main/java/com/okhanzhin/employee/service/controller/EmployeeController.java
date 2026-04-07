package com.okhanzhin.employee.service.controller;

import com.okhanzhin.employee.service.model.Employee;
import com.okhanzhin.employee.service.model.EmployeeDetailsResponse;
import com.okhanzhin.employee.service.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author okhanzhin on 31.03.2026
 */
@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    /**
     * Получить список всех сотрудников
     */
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    /**
     * Получить сотрудника по id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        var employee = employeeService.getEmployeeById(id);

        return Optional.ofNullable(employee)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Получить сотрудника вместе с рабочими пространствами
     */
    @GetMapping("/{id}/details")
    public ResponseEntity<EmployeeDetailsResponse> getEmployeeDetails(@PathVariable Long id) {
        var details = employeeService.getEmployeeDetailsById(id);

        return Optional.ofNullable(details)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Добавить нового сотрудника
     */
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        Employee created = employeeService.addEmployee(employee);
        return ResponseEntity.ok(created);
    }
}

