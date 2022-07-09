package com.springboot.db2hibernate.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.springboot.db2hibernate.model.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long>{

}
