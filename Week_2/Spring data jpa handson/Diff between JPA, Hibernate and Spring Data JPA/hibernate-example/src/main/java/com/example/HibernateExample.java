package com.example;

import java.util.List;

public class HibernateExample {
    
    public static void main(String[] args) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        System.out.println("=== Hibernate Example ===");
        System.out.println("Demonstrating manual session and transaction management\n");
        
        // Create employees
        System.out.println("1. Creating employees...");
        Integer emp1Id = employeeDAO.addEmployee(new Employee("John", "Doe", "john.doe@example.com", 50000.0));
        Integer emp2Id = employeeDAO.addEmployee(new Employee("Jane", "Smith", "jane.smith@example.com", 60000.0));
        Integer emp3Id = employeeDAO.addEmployee(new Employee("Bob", "Johnson", "bob.johnson@example.com", 55000.0));
        
        System.out.println("Created employee with ID: " + emp1Id);
        System.out.println("Created employee with ID: " + emp2Id);
        System.out.println("Created employee with ID: " + emp3Id);
        
        // List all employees
        System.out.println("\n2. Listing all employees:");
        List<Employee> employees = employeeDAO.listEmployees();
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        
        // Update an employee
        System.out.println("\n3. Updating employee salary...");
        employeeDAO.updateEmployee(emp1Id, 65000.0);
        
        // List employees after update
        System.out.println("\n4. Listing employees after update:");
        employees = employeeDAO.listEmployees();
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        
        // Delete an employee
        System.out.println("\n5. Deleting employee...");
        employeeDAO.deleteEmployee(emp2Id);
        
        // List employees after deletion
        System.out.println("\n6. Listing employees after deletion:");
        employees = employeeDAO.listEmployees();
        for (Employee emp : employees) {
            System.out.println(emp);
        }
        
        System.out.println("\n=== Hibernate Example Completed ===");
        System.out.println("Notice the manual session and transaction management required!");
        
        // Shutdown
        EmployeeDAO.shutdown();
    }
}
