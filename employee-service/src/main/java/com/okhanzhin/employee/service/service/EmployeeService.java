package com.okhanzhin.employee.service.service;

import com.okhanzhin.employee.service.client.WorkstationClient;
import com.okhanzhin.employee.service.model.Employee;
import com.okhanzhin.employee.service.model.EmployeeDetailsResponse;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления сотрудниками
 *
 * @author okhanzhin on 31.03.2026
 */
@Service
@Getter
public class EmployeeService {

    private final WorkstationClient workstationClient;
    private final List<Employee> employees;

    public EmployeeService(WorkstationClient workstationClient) {
        this.workstationClient = workstationClient;
        this.employees = new ArrayList<>();
        initializeEmployees();
    }

    /**
     * Инициализация in-memory списка сотрудников
     */
    private void initializeEmployees() {
        employees.add(new Employee(1L, "Иван", "Петров", LocalDate.of(2024, 1, 15)));
        employees.add(new Employee(2L, "Мария", "Сидорова", LocalDate.of(2023, 6, 1)));
        employees.add(new Employee(3L, "Петр", "Иванов", LocalDate.of(2025, 3, 20)));
        employees.add(new Employee(4L, "Анна", "Смирнова", LocalDate.of(2024, 11, 10)));
    }

    /**
     * Получить всех сотрудников
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    /**
     * Получить сотрудника по id
     */
    public Employee getEmployeeById(Long id) {
        return employees.stream()
                .filter(emp -> emp.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Получить сотрудника вместе с рабочими пространствами
     */
    public EmployeeDetailsResponse getEmployeeDetailsById(Long employeeId) {
        return Optional.ofNullable(getEmployeeById(employeeId))
                .map(employee -> new EmployeeDetailsResponse(
                        employee,
                        workstationClient.getWorkstationByEmployeeId(employeeId)
                ))
                .orElse(null);
    }

    /**
     * Добавить нового сотрудника
     */
    public Employee addEmployee(Employee employee) {
        employee.setId((long) (employees.size() + 1));
        employees.add(employee);
        return employee;
    }
}

