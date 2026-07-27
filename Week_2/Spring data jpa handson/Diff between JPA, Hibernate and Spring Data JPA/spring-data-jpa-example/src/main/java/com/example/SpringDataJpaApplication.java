package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringDataJpaApplication implements CommandLineRunner {
    
    @Autowired
    private EmployeeService employeeService;
    
    public static void main(String[] args) {
        SpringApplication.run(SpringDataJpaApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Spring Data JPA Example ===");
        System.out.println("Demonstrating automatic transaction and session management\n");
        
        // Create employees
        System.out.println("1. Creating employees...");
        Employee emp1 = new Employee("John", "Doe", "john.doe@example.com", 50000.0);
        Employee emp2 = new Employee("Jane", "Smith", "jane.smith@example.com", 60000.0);
        Employee emp3 = new Employee("Bob", "Johnson", "bob.johnson@example.com", 55000.0);
        
        employeeService.addEmployee(emp1);
        employeeService.addEmployee(emp2);
        employeeService.addEmployee(emp3);
        
        System.out.println("Employees created successfully!");
        
        // List all employees
        System.out.println("\n2. Listing all employees:");
        List<Employee> employees = employeeService.getAllEmployees();
        employees.forEach(System.out::println);
        
        // Update an employee
        System.out.println("\n3. Updating employee salary...");
        if (!employees.isEmpty()) {
            Employee firstEmployee = employees.get(0);
            firstEmployee.setSalary(65000.0);
            employeeService.updateEmployee(firstEmployee);
            System.out.println("Updated employee: " + firstEmployee);
        }
        
        // List employees after update
        System.out.println("\n4. Listing employees after update:");
        employees = employeeService.getAllEmployees();
        employees.forEach(System.out::println);
        
        // Delete an employee
        System.out.println("\n5. Deleting employee...");
        if (employees.size() > 1) {
            Employee employeeToDelete = employees.get(1);
            employeeService.deleteEmployee(employeeToDelete.getId());
            System.out.println("Deleted employee with ID: " + employeeToDelete.getId());
        }
        
        // List employees after deletion
        System.out.println("\n6. Listing employees after deletion:");
        employees = employeeService.getAllEmployees();
        employees.forEach(System.out::println);
        
        // Demonstrate custom queries
        System.out.println("\n7. Demonstrating custom queries:");
        List<Employee> highSalaryEmployees = employeeService.getEmployeesWithSalaryGreaterThan(50000.0);
        System.out.println("Employees with salary > 50000:");
        highSalaryEmployees.forEach(System.out::println);
        
        System.out.println("\n=== Spring Data JPA Example Completed ===");
        System.out.println("Notice how Spring Data JPA handled transactions and sessions automatically!");
    }
}
