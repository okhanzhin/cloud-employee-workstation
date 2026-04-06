package com.okhanzhin.employee.service.client;

import com.okhanzhin.employee.service.model.Workstation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "workstation-service", path = "/api/workstations")
public interface WorkstationClient {

	@GetMapping("/employee/{employeeId}")
	Workstation getWorkstationByEmployeeId(@PathVariable("employeeId") Long employeeId);
}

