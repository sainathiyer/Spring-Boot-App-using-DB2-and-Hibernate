package com.springboot.db2hibernate.service.impl;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springboot.db2hibernate.exception.ResourceNotFoundException;
import com.springboot.db2hibernate.model.Employee;
import com.springboot.db2hibernate.repository.EmployeeRepo;

@Service
public class ExternalApiConsumeService {
	
	private final RestTemplate restTemplate;
	
	private EmployeeRepo employeeRepo;
	
	@Autowired
	public ExternalApiConsumeService(RestTemplate restTemplate, EmployeeRepo employeeRepo) {
		this.restTemplate = restTemplate;
		this.employeeRepo = employeeRepo;
	}
	
	public Employee consumeApi(long id) {
		Employee employeeObj = restTemplate.getForObject("https://jsonplaceholder.typicode.com/todos/"+id, Employee.class);
		Employee existingEmployee = employeeRepo.findById(id).orElse(null);
		if(Objects.nonNull(existingEmployee)) {
		existingEmployee.setUserId(employeeObj.getUserId());
		existingEmployee.setTitle(employeeObj.getTitle());
		existingEmployee.setCompleted(employeeObj.getCompleted());
		employeeRepo.save(existingEmployee);
		return existingEmployee;
		}else {
			throw new ResourceNotFoundException("Employee","Id", id);
		}
	}
	

}
