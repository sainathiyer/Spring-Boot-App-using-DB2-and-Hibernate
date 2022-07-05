package com.springboot.db2hibernate.model;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
@Table(name = "employees")
public class Employee {
	private long id;
	private String firstName;
	private String lastName;
	private String email;

}
