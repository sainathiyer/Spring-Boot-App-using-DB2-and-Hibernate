package com.springboot.db2hibernate.service;

import java.util.List;

import com.springboot.db2hibernate.model.Employee;

public interface EmployeeService {

	public Employee saveEmployee(Employee employee);
	public List<Employee> listAll();
	public Employee getEmployeeById(long id);
	public Employee updateEmployee(Employee employee, long id);
	public void deleteEmployee(long id);
	public List<Employee> searchEmployees(String query);
}
