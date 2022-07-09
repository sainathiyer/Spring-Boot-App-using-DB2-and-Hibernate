package com.springboot.db2hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.springboot.db2hibernate.repository")
public class Db2hibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(Db2hibernateApplication.class, args);
	}

}
