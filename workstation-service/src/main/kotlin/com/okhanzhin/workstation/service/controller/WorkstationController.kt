package com.okhanzhin.workstation.service.controller

import com.okhanzhin.workstation.service.model.Workstation
import com.okhanzhin.workstation.service.service.WorkstationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/workstations")
class WorkstationController(
	private val workstationService: WorkstationService,
) {
	@GetMapping("/employee/{employeeId}")
	fun getWorkstationByEmployeeId(@PathVariable employeeId: Long): ResponseEntity<Workstation> =
		workstationService.getWorkstationByEmployeeId(employeeId)
			?.let { ResponseEntity.ok(it) }
			?: ResponseEntity.notFound().build()
}