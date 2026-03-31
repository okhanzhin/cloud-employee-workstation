package com.okhanzhin.employee.service;

import com.okhanzhin.employee.service.model.Employee;
import com.okhanzhin.employee.service.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EmployeeServiceTest {

    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        employeeService = new EmployeeService();
    }

    @Test
    void getAllEmployees_shouldReturnAllInitialEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();

        assertThat(employees).hasSize(4);
    }

    @Test
    void getAllEmployees_shouldReturnCopy_notOriginalList() {
        List<Employee> first = employeeService.getAllEmployees();
        List<Employee> second = employeeService.getAllEmployees();

        assertThat(first).isNotSameAs(second);
        assertThat(first).isEqualTo(second);
    }

    @Test
    void getEmployeeById_existingId_shouldReturnEmployee() {
        Employee employee = employeeService.getEmployeeById(1L);

        assertThat(employee).isNotNull();
        assertThat(employee.getId()).isEqualTo(1L);
        assertThat(employee.getName()).isEqualTo("Иван");
        assertThat(employee.getSurname()).isEqualTo("Петров");
    }

    @Test
    void getEmployeeById_nonExistingId_shouldReturnNull() {
        Employee employee = employeeService.getEmployeeById(999L);

        assertThat(employee).isNull();
    }

    @Test
    void addEmployee_shouldAssignIdAndAddToList() {
        int sizeBefore = employeeService.getAllEmployees().size();
        Employee newEmployee = new Employee(null, "Алексей", "Козлов", LocalDate.of(2026, 3, 31));

        Employee created = employeeService.addEmployee(newEmployee);

        assertThat(created.getId()).isEqualTo(sizeBefore + 1);
        assertThat(employeeService.getAllEmployees()).hasSize(sizeBefore + 1);
    }

    @Test
    void addEmployee_shouldReturnEmployeeWithCorrectData() {
        Employee newEmployee = new Employee(null, "Елена", "Новикова", LocalDate.of(2026, 1, 1));

        Employee created = employeeService.addEmployee(newEmployee);

        assertThat(created.getName()).isEqualTo("Елена");
        assertThat(created.getSurname()).isEqualTo("Новикова");
        assertThat(created.getDateOfEmployment()).isEqualTo(LocalDate.of(2026, 1, 1));
    }

    @Test
    void getEmployeeById_afterAdd_shouldFindNewEmployee() {
        Employee newEmployee = new Employee(null, "Сергей", "Орлов", LocalDate.of(2026, 3, 31));
        Employee created = employeeService.addEmployee(newEmployee);

        Employee found = employeeService.getEmployeeById(created.getId());

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Сергей");
    }
}

