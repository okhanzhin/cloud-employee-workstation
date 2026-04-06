package com.okhanzhin.employee.service.service;

import com.okhanzhin.employee.service.client.WorkstationClient;
import com.okhanzhin.employee.service.model.Employee;
import com.okhanzhin.employee.service.model.EmployeeDetailsResponse;
import com.okhanzhin.employee.service.model.Workstation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeDetailsService {
	private final EmployeeService employeeService;
	private final WorkstationClient workstationClient;

	public EmployeeDetailsResponse getEmployeeDetailsById(Long employeeId) {
		Employee employee = employeeService.getEmployeeById(employeeId);
		if (employee == null) {
			return null;
		}

		Workstation workstation = workstationClient.getWorkstationByEmployeeId(employeeId);
		return new EmployeeDetailsResponse(employee, workstation);
	}
}

