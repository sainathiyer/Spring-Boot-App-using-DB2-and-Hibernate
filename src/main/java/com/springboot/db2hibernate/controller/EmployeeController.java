package com.springboot.db2hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.db2hibernate.model.Employee;
import com.springboot.db2hibernate.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService employeeService;

	@Autowired
	public void setEmployeeService(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	// Create Employee REST API
	@PostMapping
	public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) {
		return new ResponseEntity<Employee>(employeeService.saveEmployee(employee), HttpStatus.CREATED);
	}
	
	// Get All Employees REST API
	@GetMapping
	public List<Employee> listAll() {
		return employeeService.listAll();
	}
	
	// Get Employee By Id REST API
	@GetMapping("{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId){
		return new ResponseEntity<Employee>(employeeService.getEmployeeById(employeeId), HttpStatus.OK);
	}
	
	
}
