package com.springboot.db2hibernate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.db2hibernate.model.Employee;
import com.springboot.db2hibernate.service.EmployeeService;
import com.springboot.db2hibernate.service.impl.ExternalApiConsumeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	private EmployeeService employeeService;
	
	private ExternalApiConsumeService externalApiConsumeService;
	
	@Autowired
	public void setExternalApiConsumeService(ExternalApiConsumeService externalApiConsumeService) {
		this.externalApiConsumeService = externalApiConsumeService;
	}

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
	
	// Update Employee REST API
	@PutMapping("{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long id, @RequestBody Employee employee){
		return new ResponseEntity<Employee>(employeeService.updateEmployee(employee, id), HttpStatus.OK);
	}
	
	// Delete Employee REST API
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEmployee(@PathVariable("id") long id){
		employeeService.deleteEmployee(id);
		return new ResponseEntity<String>("Employee Deleted Successfully", HttpStatus.OK);
	}
	
	// Consuming External REST API
	@PutMapping("/consume/{id}")
	public ResponseEntity<Employee> updateData(@PathVariable("id") long id) {
		return new ResponseEntity<Employee>(externalApiConsumeService.consumeApi(id), HttpStatus.OK);
	}
	
	// Search Employee REST API
	@GetMapping("/search")
	public ResponseEntity<List<Employee>> searchEmployees(@RequestParam("query") String query){
		return new ResponseEntity<List<Employee>>(employeeService.searchEmployees(query), HttpStatus.OK);
	}
	
	
}
