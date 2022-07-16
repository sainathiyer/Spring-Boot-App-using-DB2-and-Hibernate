# Spring-Boot-App-using-DB2-and-Hibernate
Created a simple Spring Boot App using IBM's DB2 Database and Hibernate, demonstrating the implementation of JPA.

# Spring Boot Project Architecture:
# Here we follow a 3 layer architecture or 3 tier architecture:
1) Controller Layer (Rest End Points/API Logic)
2) Service Layer (Business Logic)
3) Repository or DAO Layer (Persistence Logic)

All REST APIs return JSON output appropriate for the API invoked. HTTP Status codes 
other than 200 are used as appropriate to indicate various failures, along with JSON for detailed error messages.

# Each of these layers has their own responsibilities:
1) Controller Layer is responsible to process all the requests sent by the client, its also called as API layer.
Because we define all the REST API's (GET, PUT, POST, DELETE) in Controller Layer.

# Method   Description
a) GET:    Retrieve information about the REST API resource
b) POST:   Create a REST API resource
c) PUT:	   Update a REST API resource
d) DELETE: Delete a REST API resource or related component

2) Service Layer is responsible to maintain all the Business Logic. We should keep all the application level 
business logic in the Service Layer. Also apart from business logic if you have any third party REST API call, so that logic can be kept in the service layer.

3) Repository Layer is responsible to talk with the database, which is in this case is DB2. This layer holds all the Persistence Logic. 

# These are the 3 layers that we are going to create in our Spring Boot Application.

In a Controller we call a Service and in a Service we call a Repository. [Controller -> Service -> Repository].
So basically in Spring Based Applications we Inject Service class or Service Interface in a Controller, and then we call Service class methods in 
a Controller.
Similarly we Inject Repository in Service class and then we call Repository class methods in a Service. 
So these 3 layers are Independent to each other and we have to Inject the dependencies and then call their methods. 
And we use Postman REST Client to test all the REST End Points.

# Dependencies required from Spring Initializer Tool:
1) IBM DB2 Driver: A JDBC driver that provides access to IBM DB2.
2) Spring Web: Build web, including RESTful, applications using Spring MVC. Uses Apache Tomcat as the default embedded container.
3) Spring Data JPA: Persist data in SQL stores with Java Persistence API using Spring Data and Hibernate.
4) Lombok: Java annotation library which helps to reduce boilerplate code.

In this project we have used Gradle since its much faster than Maven. So in the project folder we need to run the command "gradlew clean build"
and check for "BUILD SUCCESSFUL" message. This will indicate that all the dependencies are loaded successfully.

# Then we need to create a package structure:
com.springboot.db2hibernate.controller
com.springboot.db2hibernate.service
com.springboot.db2hibernate.service.impl
com.springboot.db2hibernate.exception
com.springboot.db2hibernate.repository
com.springboot.db2hibernate.model OR com.springboot.db2hibernate.entity OR com.springboot.db2hibernate.domain

So now we have a packaging structure ready for our Spring Boot Project

Now we need to configure the DB2 Database for the project:
In DB2 Admin Console run the following command: CREATE DATABASE EMS
This will create a database called as "EMS" i,e Employee Management System

In src/main/resources we have application.properties file. So for the Repository/DAO Layer to talk with the DB2 Database 
we need the following properties to be configured in this application.properties file.
1) JDBC Driver
2) JDBC URL
3) Username
4) Password

spring.datasource.url=jdbc:db2://localhost:25000/EMS
spring.datasource.username=db2admin
spring.datasource.password=
spring.datasource.driver-class-name=com.ibm.db2.jcc.DB2Driver

After this we need to provide the Hibernate properties in this file:
Hibernate needs a Dialect to generate vendor specific SQL queries. We need to configure one more hibernate property i,e auto-ddl.
This property is needed to automatically create tables (DDL Statements) in the DB2 database.

#Hibernate Properties 
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.DB2Dialect
#Create, Create-Drop
spring.jpa.hibernate.ddl-auto=update

Entity file is created in the "com.springboot.db2hibernate.model" package. Here we define the tables, columns and their mappings.
In the Entity file we created, we don't create getters and setters methods, or constructors or toString(). Instead we use Lombok annotations i,e 
@Data annotation, it internally uses @Getter, @Setter, @ToString etc. to generate the required methods.
@Entity annotation is used to convert a normal class into a JPA Entity. (Choose javax.persistence package)
@Table(name = " ") annotation is used to provide a table name to the Entity class. (Choose javax.persistence package) This annotation has an attribute "name" which can 
be used to provide a String as a table name. Even if we don't provide a table name explicitly using the @Table annotation, 
then JPA is smart enough to provide a table name, same as that of the class.
@Id annotation specifies the Primary Key of the Entity. (Choose javax.persistence package)
Once we create a Primary Key we also need to define a primary key generation strategy for that we use the
@GeneratedValue(strategy = GenerationType.IDENTITY) annotation.
@Column(name = " ", nullable = false) annotation is used to provide a column name. We can also define our column to be NOT NULL using the "nullable = false" attribute.

After creating the Entity file, we can run the Spring Boot App and check if the Employee Table is created in DB or not. So Hibernate with automatically generate
these tables, because we have added a property called as "ddl-auto".

Now we should create Repository interface. We need to create this Repository in order to perform CRUD operations on the Entity. 
Remember this is an interface hence it needs to extend to the JPA Repository Class. JpaRepository<T, ID> requires two parameters 
1) Type of the Entity
2) Type of the Primary Key
Here we don't have to provide @Repository annotation, because SimpleJpaRepository class internally provides @Repository annotation.
It also provides @Transactional annotation internally, hence we don't need to provide this annotation in Service layer.

Before building REST APIs we need to create one custom Exception i,e ResourceNotFoundException class. So whenever a resource does not exist in the database or 
whenever a record does not exist in database then our REST API should return to the client saying that "this resource does not exist in the database." And this class 
extends from RuntimeException. And we need to add @ResponseStatus(value = HttpStatus.NOT_FOUND) annotation to this Exception Class, 
instead of adding this to all the REST APIs we create. So whenever our REST API will throw ResourceNotFoundException then that REST API will send HTTP Status 
as "NOT FOUND" to the client.

Now we will develop the Service Layer, because our Controller Layer depends on the Service Layer. 
So we create an interface in the "com.springboot.db2hibernate.service" package. Then we develop a class which implements this Service interface in
"com.springboot.db2hibernate.service.impl" package.
For eg. EmployeeServiceImpl is a class which implements EmployeeService interface. (Naming Convention)
To this class we add the @Service annotation.

Then in the "Service" interface we declare a "saveEmployee()" method whose implementation is done in the "Impl" class.
So in the "Impl" class we add the "unimplemented methods". Before implementing this method we need to inject the "Repository" dependency.

# So basically there are two types of dependency injection:
1) Setter Based Dependency Injection (We should always use Setter Based Dependency Injection when we have Optional parameters)
2) Constructor Based Dependency Injection (We should always use Constructor Based Dependency Injection when we have Mandatory parameters)

In this case we have Mandatory field so we use Constructor Based Dependency Injection.
Whenever we have a class which is configured as Spring Bean (Annotated using @ annotations) and it has only ONE constructor, then @Autowired annotation can be
omitted for that constructor and Spring will use that constructor and inject all the necessary dependencies and automatically Autowire it.

Then we need to define the "saveEmployee()" method which we declared in the interface, in the "Impl" class as:
@Override
public Employee saveEmployee(Employee employee) {
	return employeeRepo.save(employee);
}

This will save the employee instance to the database. This completes our Service Layer for saveEmployee or createEmployee REST API.

Now we create a REST API in Controller Layer. So in the "com.springboot.db2hibernate.controller" package, we create a Controller class.
And annotate it with @RestController annotation.
Here we can also use @Controller annotation, but then we will have to use @ResponseBody annotation, on top of each and every REST API that we define inside this 
controller.
@RestController is a convenient annotation that combines @Controller and @ResponseBody (internally uses both of them) which eliminates the need to 
annotate every request handling method of the controller class.
Now we inject the Service dependency in this controller and again we will use Constructor Based Dependency Injection.

@PostMapping annotation is used to handle Post HTTP request that creates a record. And to this method we pass the Employee object. Because this Post 
request contains an Employee JSON object and that we need to bind to the Java Employee Object for that we are going to use @RequestBody annotation.
@RequestBody annotation allows us to retrieve the request's body and automatically convert it to Java Object.

Now we have built the saveEmployee() REST API to create an Employee record and save employee data to the database.
Next we create getAllEmployees() REST API to retrieve all the Employees from the database table.

Now we can create a getEmployeeById() REST API to retrieve details of a specific employee by providing "id" of that employee. We annotate this API 
with @GetMapping annotation. And provide the path variable as "{id}" in the @GetMapping annotation.
Similarly we use @PathVariable("id") as an argument to the getEmployeeById() ResponseEntity method in the Controller file.

In the updateEmployee() REST API we have used the @PutMapping controller. Now we are going to store updateEmployee() REST API Request Body into some object.
The Request Body contains JSON, so we need to convert that JSON into Java Object for that we need to use @RequestBody annotation.

# Updates:
1) Added feature to Consume External REST API: Convert the JSON Information to Java Object using "RestTemplate" and Store it in the DB2 Database.
@JsonIgnoreProperties can be used at class level to mark a property or list of properties to be ignored. Refer: https://www.youtube.com/watch?v=QPiAhiUwb4I

2) Added Production-grade Services such as health, audits, beans, and more using the "Actuator" module.
For this I have added "implementation 'org.springframework.boot:spring-boot-starter-actuator'" in the build.gradle file.
And after that we need to perform Gradle -> Refresh Gradle Project in Eclipse and then restart the application.
Open the browser to invoke the URL "http://localhost:8080/actuator". So the output shows three endpoint URLs.

{
"_links":{"self":{"href":"http://localhost:8080/actuator","templated":false},
"health":{"href":"http://localhost:8080/actuator/health","templated":false},
"health-path":{"href":"http://localhost:8080/actuator/health/{*path}","templated":true}
   }
}

After accessing the default /health endpoint by pointing the browser to "http://localhost:8080/actuator/health"
The output is this:

{
"status":"UP"
}

The above output denotes the status "UP". It means the application is healthy and running without any interruption.
You can enable all of the built-in endpoints of Actuator. To do so, set the configuration in the application.properties file, like this.
"management.endpoints.web.exposure.include=*"

3) Added Search or Filter REST API:
Created JPQL and Native SQL Queries for Search REST API. Here we use the "@Query()" annotation from Spring Data JPA.
JPQL is Java Persistence Query Language defined in JPA specification. It is used to create queries against entities to store in a relational database. 
JPQL is developed based on SQL syntax.
When using JPQL Query we have to provide Class names and Field names. And when using SQL Query we have to provide Table names and Column names.
Within the JPQL Query we use the Named Parameter ":query" to pass the value. For named parameter we use "@Param("query")" annotation in the Repo file.
Whenever we pass the Native SQL query to "@Query()" annotation we have to enable the attribute "nativeQuery = true".
To use this Search REST API we use "?query=" parameter in the URL.

