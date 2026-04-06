package com.okhanzhin.workstation.service.service

import com.okhanzhin.workstation.service.model.Workstation
import org.springframework.stereotype.Service

@Service
class WorkstationService {
    private val workstationsByEmployeeId: Map<Long, Workstation> = mapOf(
        1L to Workstation(1L, "Lenovo ThinkPad T14", "Windows 11"),
        2L to Workstation(2L, "MacBook Pro 14", "macOS Sonoma"),
        3L to Workstation(3L, "Dell Precision 3580", "Ubuntu 24.04"),
        4L to Workstation(4L, "HP EliteBook 840", "Windows 11"),
    )

    fun getWorkstationByEmployeeId(employeeId: Long): Workstation? =
        workstationsByEmployeeId[employeeId]
}
