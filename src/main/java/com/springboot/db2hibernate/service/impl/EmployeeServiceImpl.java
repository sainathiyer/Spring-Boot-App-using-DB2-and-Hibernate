package com.springboot.db2hibernate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.db2hibernate.exception.ResourceNotFoundException;
import com.springboot.db2hibernate.model.Employee;
import com.springboot.db2hibernate.repository.EmployeeRepo;
import com.springboot.db2hibernate.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	private EmployeeRepo employeeRepo;

	@Autowired
	public EmployeeServiceImpl(EmployeeRepo employeeRepo) {
	    this.employeeRepo = employeeRepo;
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		employeeRepo.save(employee);
		return employee;
	}

	@Override
    public List<Employee> listAll() {
        List<Employee> employees = new ArrayList<>();
        employeeRepo.findAll().forEach(employees::add);
        return employees;
    }

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> employee = employeeRepo.findById(id);
		if(employee.isPresent()) {
			return employee.get();
		}else {
			throw new ResourceNotFoundException("Employee","Id", id);
		}
	}

}