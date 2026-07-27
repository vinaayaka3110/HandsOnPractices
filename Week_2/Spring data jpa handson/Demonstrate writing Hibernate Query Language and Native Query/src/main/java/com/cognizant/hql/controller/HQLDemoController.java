package com.cognizant.hql.controller;

import com.cognizant.hql.service.QueryDemonstrationService;
import com.cognizant.hql.repository.DepartmentRepository;
import com.cognizant.hql.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller to trigger HQL, JPQL, and Native Query demonstrations
 */
@RestController
@RequestMapping("/api/hql-demo")
public class HQLDemoController {
    
    @Autowired
    private QueryDemonstrationService queryDemonstrationService;
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    /**
     * Endpoint to run all query demonstrations
     */
    @GetMapping("/run-all")
    public ResponseEntity<String> runAllDemonstrations() {
        try {
            System.out.println("Starting HQL demonstrations...");
            queryDemonstrationService.demonstrateAllQueries();
            return ResponseEntity.ok("All HQL, JPQL, and Native Query demonstrations completed successfully! " +
                    "Check the application logs for detailed results.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Error running demonstrations: " + e.getMessage() + 
                          " - Stack trace: " + java.util.Arrays.toString(e.getStackTrace()));
        }
    }
    
    /**
     * Simple health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("HQL Demo Application is running!");
    }
    
    /**
     * Simple test endpoint to check data availability
     */
    @GetMapping("/test")
    public ResponseEntity<String> test() {
        try {
            long deptCount = departmentRepository.count();
            long empCount = employeeRepository.count();
            return ResponseEntity.ok("Database test successful! " +
                    "Departments: " + deptCount + ", Employees: " + empCount);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Database test failed: " + e.getMessage());
        }
    }
    
    /**
     * Simple test endpoint to run basic queries
     */
    @GetMapping("/basic-test")
    public ResponseEntity<String> basicTest() {
        try {
            StringBuilder result = new StringBuilder();
            result.append("=== BASIC QUERY TEST ===\n");
            
            // Test basic repository queries
            long deptCount = departmentRepository.count();
            long empCount = employeeRepository.count();
            result.append("Database counts - Departments: ").append(deptCount)
                  .append(", Employees: ").append(empCount).append("\n");
            
            // Test basic find all
            var departments = departmentRepository.findAll();
            result.append("Departments found: ").append(departments.size()).append("\n");
            departments.forEach(dept -> 
                result.append("- ").append(dept.getName()).append(" (Budget: $").append(dept.getBudget()).append(")\n"));
            
            // Test basic employee query
            var employees = employeeRepository.findAll();
            result.append("Employees found: ").append(employees.size()).append("\n");
            employees.stream().limit(5).forEach(emp -> 
                result.append("- ").append(emp.getFirstName()).append(" ").append(emp.getLastName())
                      .append(" (").append(emp.getPosition()).append(")\n"));
            
            return ResponseEntity.ok(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                    .body("Basic test failed: " + e.getMessage());
        }
    }
}
