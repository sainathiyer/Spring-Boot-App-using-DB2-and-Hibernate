package com.springboot.db2hibernate.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.db2hibernate.model.Employee;

@Repository
public interface EmployeeRepo extends CrudRepository<Employee, Long>{
	
	@Query("SELECT e FROM Employee e WHERE " +
	"e.firstName LIKE CONCAT('%', :query, '%')" + 
	"OR e.lastName LIKE CONCAT('%', :query, '%')")
	List<Employee> searchEmployees(@Param("query") String query);
	
}
