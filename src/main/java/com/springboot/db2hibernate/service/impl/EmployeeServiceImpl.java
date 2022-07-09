package com.springboot.db2hibernate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.db2hibernate.model.Employee;
import com.springboot.db2hibernate.repository.EmployeeRepo;
import com.springboot.db2hibernate.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public Employee saveEmployee(Employee employee) {
		employeeRepo.save(employee);
		return employee;
	}
	
	

}
