package com.springboot.db2hibernate.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

	@Override
	public Employee updateEmployee(Employee employee, long id) {
		//Checking whether an employee with given id exists in the database or not
		Employee existingEmployee = employeeRepo.findById(id).orElse(null);
		if(Objects.nonNull(existingEmployee)) {
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		employeeRepo.save(existingEmployee);
		return existingEmployee;
		}else {
			throw new ResourceNotFoundException("Employee","Id", id);
		}
	}

	@Override
	public void deleteEmployee(long id) {
		//Checking whether an employee with given id exists in the database or not
		if(Objects.nonNull(employeeRepo.findById(id))) { 
			employeeRepo.deleteById(id);
		}else {
			throw new ResourceNotFoundException("Employee","Id", id);
		}
	}

}
