package com.okhanzhin.employee.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.okhanzhin.employee.service.controller.EmployeeController;
import com.okhanzhin.employee.service.model.Employee;
import com.okhanzhin.employee.service.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private EmployeeService employeeService;

    @Test
    void getAllEmployees_shouldReturnListAndStatus200() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "Иван", "Петров", LocalDate.of(2024, 1, 15)),
                new Employee(2L, "Мария", "Сидорова", LocalDate.of(2023, 6, 1))
        );
        when(employeeService.getAllEmployees()).thenReturn(employees);

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Иван")))
                .andExpect(jsonPath("$[0].surname", is("Петров")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Мария")));
    }

    @Test
    void getAllEmployees_shouldReturnEmptyList() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of());

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getEmployeeById_existingId_shouldReturnEmployeeAndStatus200() throws Exception {
        Employee employee = new Employee(1L, "Иван", "Петров", LocalDate.of(2024, 1, 15));
        when(employeeService.getEmployeeById(1L)).thenReturn(employee);

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Иван")))
                .andExpect(jsonPath("$.surname", is("Петров")))
                .andExpect(jsonPath("$.dateOfEmployment", is("2024-01-15")));
    }

    @Test
    void getEmployeeById_nonExistingId_shouldReturnStatus404() throws Exception {
        when(employeeService.getEmployeeById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addEmployee_shouldReturnCreatedEmployeeAndStatus200() throws Exception {
        Employee request = new Employee(null, "Алексей", "Козлов", LocalDate.of(2026, 3, 31));
        Employee created = new Employee(5L, "Алексей", "Козлов", LocalDate.of(2026, 3, 31));
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(created);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.name", is("Алексей")))
                .andExpect(jsonPath("$.surname", is("Козлов")))
                .andExpect(jsonPath("$.dateOfEmployment", is("2026-03-31")));
    }
}

