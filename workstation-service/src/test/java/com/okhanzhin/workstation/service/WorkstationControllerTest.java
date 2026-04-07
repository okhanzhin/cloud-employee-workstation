package com.okhanzhin.workstation.service;

import com.okhanzhin.workstation.service.controller.WorkstationController;
import com.okhanzhin.workstation.service.model.Workstation;
import com.okhanzhin.workstation.service.service.WorkstationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = WorkstationController.class,
        properties = {
                "spring.config.import=optional:configserver:",
                "spring.cloud.config.enabled=false",
                "spring.cloud.config.import-check.enabled=false",
                "eureka.client.enabled=false"
        }
)
class WorkstationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WorkstationService workstationService;

    @Test
    void getWorkstationByEmployeeId_shouldReturnEmployeeWorkstation() throws Exception {
        when(workstationService.getWorkstationByEmployeeId(1L)).thenReturn(
                new Workstation(1L, "Lenovo ThinkPad T14", "Windows 11")
        );

        mockMvc.perform(get("/api/workstations/employee/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Lenovo ThinkPad T14")))
                .andExpect(jsonPath("$.os", is("Windows 11")));
    }
}

