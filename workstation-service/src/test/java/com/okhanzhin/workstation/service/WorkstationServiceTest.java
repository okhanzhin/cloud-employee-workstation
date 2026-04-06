package com.okhanzhin.workstation.service;

import com.okhanzhin.workstation.service.model.Workstation;
import com.okhanzhin.workstation.service.service.WorkstationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WorkstationServiceTest {

    private WorkstationService workstationService;

    @BeforeEach
    void setUp() {
        workstationService = new WorkstationService();
    }

    @Test
    void getWorkstationByEmployeeId_existingEmployee_shouldReturnInMemoryData() {
        Workstation workstation = workstationService.getWorkstationByEmployeeId(1L);

        assertThat(workstation).isNotNull();
        assertThat(workstation.getName()).isEqualTo("Lenovo ThinkPad T14");
        assertThat(workstation.getOs()).isEqualTo("Windows 11");
    }

    @Test
    void getWorkstationByEmployeeId_unknownEmployee_shouldReturnNull() {
        assertThat(workstationService.getWorkstationByEmployeeId(999L)).isNull();
    }
}
