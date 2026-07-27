package com.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    
    // Custom query methods can be added here
    // Spring Data JPA will automatically implement these methods
    
    // Find employees by first name
    java.util.List<Employee> findByFirstName(String firstName);
    
    // Find employees by email
    Employee findByEmail(String email);
    
    // Find employees with salary greater than specified amount
    java.util.List<Employee> findBySalaryGreaterThan(Double salary);
    
    // Find employees by first name and last name
    java.util.List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
}
