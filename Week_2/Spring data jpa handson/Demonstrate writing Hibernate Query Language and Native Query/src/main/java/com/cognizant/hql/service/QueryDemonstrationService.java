package com.cognizant.hql.service;

import com.cognizant.hql.dto.DepartmentSummaryDTO;
import com.cognizant.hql.dto.EmployeeSummaryDTO;
import com.cognizant.hql.entity.Department;
import com.cognizant.hql.entity.Employee;
import com.cognizant.hql.repository.DepartmentRepository;
import com.cognizant.hql.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class to demonstrate all HQL, JPQL, and Native Query features
 */
@Service
@Transactional
public class QueryDemonstrationService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(QueryDemonstrationService.class);
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    /**
     * Master method to demonstrate all query types
     */
    public void demonstrateAllQueries() {
        LOGGER.info("Starting comprehensive query demonstrations...");
        
        demonstrateBasicHQLQueries();
        demonstrateHQLFetchKeyword();
        demonstrateAggregateFunctions();
        demonstrateAdvancedHQLQueries();
        demonstrateProjectionQueries();
        demonstrateNativeQueries();
        demonstrateNamedQueries();
        demonstrateHQLVsJPQLComparison();
        
        LOGGER.info("All query demonstrations completed!");
    }
    
    /**
     * Demonstrates basic HQL/JPQL queries
     */
    private void demonstrateBasicHQLQueries() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("1. BASIC HQL/JPQL QUERIES DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // Basic HQL query
        LOGGER.info("🔍 Basic HQL: Find department by name");
        Department itDept = departmentRepository.findByNameHQL("Information Technology").orElse(null);
        if (itDept != null) {
            LOGGER.info("  Found: {}", itDept);
        }
        
        // HQL with LIKE operator
        LOGGER.info("🔍 HQL with LIKE: Find departments containing 'Tech'");
        List<Department> techDepts = departmentRepository.findByNameContainingHQL("Tech");
        techDepts.forEach(dept -> LOGGER.info("  Found: {}", dept.getName()));
        
        // HQL with multiple conditions
        LOGGER.info("🔍 HQL with AND: Find departments with budget > 400000 in Building A");
        List<Department> richDepts = departmentRepository.findByBudgetAndLocationHQL(400000.0, "Building A");
        richDepts.forEach(dept -> LOGGER.info("  Found: {} with budget {}", dept.getName(), dept.getBudget()));
        
        // HQL with ORDER BY
        LOGGER.info("🔍 HQL with ORDER BY: Departments with budget > 200000 ordered by budget DESC");
        List<Department> orderedDepts = departmentRepository.findByBudgetOrderedHQL(200000.0);
        orderedDepts.forEach(dept -> LOGGER.info("  Found: {} - ${}", dept.getName(), dept.getBudget()));
    }
    
    /**
     * Demonstrates HQL FETCH keyword for eager loading
     */
    private void demonstrateHQLFetchKeyword() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("2. HQL FETCH KEYWORD DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // JOIN FETCH for eager loading
        LOGGER.info("🚀 HQL JOIN FETCH: Eagerly load departments with employees (budget > 300000)");
        List<Department> deptsWithEmployees = departmentRepository.findDepartmentsWithEmployeesHQL(300000.0);
        deptsWithEmployees.forEach(dept -> {
            LOGGER.info("  Department: {} has {} employees", dept.getName(), dept.getEmployees().size());
            dept.getEmployees().forEach(emp -> 
                LOGGER.info("    Employee: {} - {}", emp.getFullName(), emp.getPosition())
            );
        });
        
        // LEFT JOIN FETCH
        LOGGER.info("🚀 HQL LEFT JOIN FETCH: All departments with their employees");
        List<Department> allDeptsWithEmployees = departmentRepository.findAllDepartmentsWithEmployeesHQL();
        LOGGER.info("  Loaded {} departments with their employees in single query", allDeptsWithEmployees.size());
        
        // Employee queries with FETCH
        LOGGER.info("🚀 Employee HQL FETCH: High earners with department info (> $70,000)");
        List<Employee> highEarners = employeeRepository.findHighEarnersWithDepartmentHQL(new BigDecimal("70000"));
        highEarners.forEach(emp -> 
            LOGGER.info("  Employee: {} earns ${} in {}", emp.getFullName(), emp.getSalary(), emp.getDepartment().getName())
        );
    }
    
    /**
     * Demonstrates aggregate functions in HQL
     */
    private void demonstrateAggregateFunctions() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("3. AGGREGATE FUNCTIONS IN HQL DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // COUNT function
        LOGGER.info("📊 HQL COUNT: Departments with budget > $400,000");
        Long richDeptCount = departmentRepository.countDepartmentsWithBudgetAboveHQL(400000.0);
        LOGGER.info("  Count: {} departments", richDeptCount);
        
        // SUM function
        LOGGER.info("📊 HQL SUM: Total budget across all departments");
        Double totalBudget = departmentRepository.getTotalBudgetHQL();
        if (totalBudget != null) {
            LOGGER.info("  Total Budget: ${:,.2f}", totalBudget);
        } else {
            LOGGER.info("  No budget data available");
        }
        
        // AVG function
        LOGGER.info("📊 HQL AVG: Average department budget");
        Double avgBudget = departmentRepository.getAverageBudgetHQL();
        if (avgBudget != null) {
            LOGGER.info("  Average Budget: ${:,.2f}", avgBudget);
        } else {
            LOGGER.info("  No budget data available for average calculation");
        }
        
        // MIN and MAX functions
        LOGGER.info("📊 HQL MIN/MAX: Minimum and Maximum department budgets");
        Object[] minMax = departmentRepository.getMinMaxBudgetHQL();
        if (minMax != null && minMax.length >= 2 && minMax[0] != null && minMax[1] != null) {
            LOGGER.info("  Min Budget: ${:,.2f}, Max Budget: ${:,.2f}", minMax[0], minMax[1]);
        } else {
            LOGGER.info("  No department budget data available");
        }
        
        // GROUP BY with aggregates
        LOGGER.info("📊 HQL GROUP BY: Department statistics by location");
        List<Object[]> locationStats = departmentRepository.getDepartmentStatsByLocationHQL();
        locationStats.forEach(stat -> 
            LOGGER.info("  Location: {}, Count: {}, Avg Budget: ${:,.2f}", 
                       stat[0], stat[1], stat[2])
        );
        
        // SIZE function for collections
        LOGGER.info("📊 HQL SIZE: Departments with employee count");
        List<Object[]> deptEmployeeCounts = departmentRepository.getDepartmentsWithEmployeeCountHQL();
        deptEmployeeCounts.forEach(result -> {
            Department dept = (Department) result[0];
            Long empCount = (Long) result[1];
            LOGGER.info("  Department: {} has {} employees", dept.getName(), empCount);
        });
        
        // Employee aggregate functions
        LOGGER.info("📊 Employee Aggregates: Salary statistics by department");
        List<Object[]> salaryStats = employeeRepository.getSalaryStatsByDepartmentHQL();
        salaryStats.forEach(stat -> 
            LOGGER.info("  Dept: {}, Count: {}, Avg Salary: ${:,.2f}, Total: ${:,.2f}", 
                       stat[0], stat[1], stat[2], stat[3])
        );
    }
    
    /**
     * Demonstrates advanced HQL queries
     */
    private void demonstrateAdvancedHQLQueries() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("4. ADVANCED HQL QUERIES DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // CASE WHEN expressions
        LOGGER.info("🎯 HQL CASE WHEN: Employee salary categorization");
        List<Object[]> salaryCategories = employeeRepository.getEmployeesWithSalaryCategorizationHQL();
        salaryCategories.forEach(result -> 
            LOGGER.info("  Employee: {} {} - ${} ({})", 
                       result[0], result[1], result[2], result[3])
        );
        
        // Subqueries
        LOGGER.info("🎯 HQL Subquery: Departments with above-average budget");
        List<Department> aboveAvgDepts = departmentRepository.findDepartmentsAboveAverageBudgetHQL();
        aboveAvgDepts.forEach(dept -> 
            LOGGER.info("  Department: {} - ${:,.2f}", dept.getName(), dept.getBudget())
        );
        
        LOGGER.info("🎯 HQL Subquery: Employees earning above their department average");
        List<Employee> aboveAvgEmps = employeeRepository.findEmployeesAboveDepartmentAverageHQL();
        aboveAvgEmps.forEach(emp -> 
            LOGGER.info("  Employee: {} earns ${} in {}", 
                       emp.getFullName(), emp.getSalary(), emp.getDepartment().getName())
        );
        
        // EXISTS subquery
        LOGGER.info("🎯 HQL EXISTS: Departments with high earners (> $80,000)");
        List<Department> deptsWithHighEarners = departmentRepository.findDepartmentsWithHighPaidEmployeesHQL(80000.0);
        deptsWithHighEarners.forEach(dept -> 
            LOGGER.info("  Department: {} has high earners", dept.getName())
        );
        
        // Date functions
        LOGGER.info("🎯 HQL Date Functions: Employees hired in 2020");
        List<Employee> hired2020 = employeeRepository.findEmployeesHiredInYearHQL(2020);
        hired2020.forEach(emp -> 
            LOGGER.info("  Employee: {} hired on {}", emp.getFullName(), emp.getHireDate())
        );
    }
    
    /**
     * Demonstrates projection queries
     */
    private void demonstrateProjectionQueries() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("5. PROJECTION QUERIES DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // Basic projection
        LOGGER.info("📋 HQL Projection: Department summary (name, budget, location)");
        List<Object[]> deptSummary = departmentRepository.getDepartmentSummaryHQL(200000.0);
        deptSummary.forEach(result -> 
            LOGGER.info("  Dept: {}, Budget: ${:,.2f}, Location: {}", 
                       result[0], result[1], result[2])
        );
        
        // Constructor expression with DTO
        LOGGER.info("📋 HQL Constructor Expression: Department summary DTOs");
        List<DepartmentSummaryDTO> deptDTOs = departmentRepository.getDepartmentSummaryDTOHQL();
        deptDTOs.forEach(dto -> LOGGER.info("  DTO: {}", dto));
        
        // Employee projections
        LOGGER.info("📋 Employee Projection: Active employee summary");
        List<Object[]> empSummary = employeeRepository.getActiveEmployeeSummaryHQL();
        empSummary.subList(0, Math.min(5, empSummary.size())).forEach(result -> 
            LOGGER.info("  Employee: {} {} - {} - ${} - {}", 
                       result[0], result[1], result[2], result[3], result[4])
        );
        
        // Employee DTO projection
        LOGGER.info("📋 Employee DTO Projection: Active employee DTOs");
        List<EmployeeSummaryDTO> empDTOs = employeeRepository.getActiveEmployeeSummaryDTOHQL();
        empDTOs.subList(0, Math.min(5, empDTOs.size())).forEach(dto -> LOGGER.info("  DTO: {}", dto));
    }
    
    /**
     * Demonstrates native SQL queries
     */
    private void demonstrateNativeQueries() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("6. NATIVE SQL QUERIES DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // Basic native query
        LOGGER.info("🔧 Native SQL: Departments with budget > $300,000");
        List<Department> nativeDepts = departmentRepository.findByBudgetNative(300000.0);
        nativeDepts.forEach(dept -> 
            LOGGER.info("  Native Query Result: {} - ${:,.2f}", dept.getName(), dept.getBudget())
        );
        
        // Native query with complex joins
        LOGGER.info("🔧 Native SQL: Department statistics with employee analytics");
        List<Object[]> deptAnalytics = employeeRepository.getDepartmentEmployeeAnalyticsNative();
        deptAnalytics.forEach(result -> 
            LOGGER.info("  Dept: {}, Employees: {}, Avg Salary: ${:,.2f}, Avg Age: {}, Avg Experience: {} years",
                       result[0], result[1], result[2], result[6], result[7])
        );
        
        // Native query with window functions
        LOGGER.info("🔧 Native SQL: Employee ranking with window functions");
        List<Object[]> empRankings = employeeRepository.getEmployeesWithRankingNative();
        empRankings.subList(0, Math.min(5, empRankings.size())).forEach(result -> {
            // Assuming Employee fields are in positions 0-10, then ranking fields
            LOGGER.info("  Employee Ranking - Salary Rank: {}, Hire Order: {}", 
                       result[11], result[12]); // Adjust indices based on actual result structure
        });
        
        // Native query for trends analysis
        LOGGER.info("🔧 Native SQL: Hiring trends by year");
        List<Object[]> hiringTrends = employeeRepository.getHiringTrendsNative();
        hiringTrends.forEach(result -> 
            LOGGER.info("  Year: {}, Employees Hired: {}, Avg Salary: ${:,.2f}", 
                       result[0], result[1], result[2])
        );
    }
    
    /**
     * Demonstrates named queries
     */
    private void demonstrateNamedQueries() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("7. NAMED QUERIES DEMONSTRATION");
        LOGGER.info("=".repeat(70));
        
        // Named query from Department entity
        LOGGER.info("📝 Named Query: Find department by name");
        Department namedDept = departmentRepository.findByNameNamed("Information Technology").orElse(null);
        if (namedDept != null) {
            LOGGER.info("  Named Query Result: {}", namedDept);
        }
        
        // Named query with employee count
        LOGGER.info("📝 Named Query: Departments with employee count");
        List<Object[]> namedResults = departmentRepository.findWithEmployeeCountNamed();
        namedResults.forEach(result -> {
            Department dept = (Department) result[0];
            Long count = (Long) result[1];
            LOGGER.info("  Named Query: {} has {} employees", dept.getName(), count);
        });
        
        // Employee named queries
        LOGGER.info("📝 Employee Named Query: IT department employees");
        List<Employee> itEmployees = employeeRepository.findByDepartmentNameNamed("Information Technology");
        itEmployees.forEach(emp -> 
            LOGGER.info("  Named Query Employee: {} - {}", emp.getFullName(), emp.getPosition())
        );
        
        LOGGER.info("📝 Employee Named Query: High earners with department fetch");
        List<Employee> fetchedEmployees = employeeRepository.findWithDepartmentFetchNamed();
        fetchedEmployees.subList(0, Math.min(3, fetchedEmployees.size())).forEach(emp -> 
            LOGGER.info("  Fetched Employee: {} works in {}", emp.getFullName(), emp.getDepartment().getName())
        );
    }
    
    /**
     * Demonstrates the comparison between HQL and JPQL
     */
    private void demonstrateHQLVsJPQLComparison() {
        LOGGER.info("\n" + "=".repeat(70));
        LOGGER.info("8. HQL vs JPQL COMPARISON");
        LOGGER.info("=".repeat(70));
        
        LOGGER.info("🔄 HQL vs JPQL Comparison:");
        LOGGER.info("  ✅ HQL (Hibernate Query Language):");
        LOGGER.info("    - Hibernate-specific query language");
        LOGGER.info("    - Superset of JPQL with additional features");
        LOGGER.info("    - Supports Hibernate-specific functions and features");
        LOGGER.info("    - Example: Hibernate-specific functions like 'elements()', 'indices()'");
        
        LOGGER.info("  ✅ JPQL (Java Persistence Query Language):");
        LOGGER.info("    - JPA standard query language");
        LOGGER.info("    - Portable across different JPA providers");
        LOGGER.info("    - More limited but standardized");
        LOGGER.info("    - Example: Standard functions like UPPER(), LOWER(), SUBSTRING()");
        
        LOGGER.info("  🎯 Key Differences:");
        LOGGER.info("    1. HQL supports more functions and operations");
        LOGGER.info("    2. JPQL is JPA standard and portable");
        LOGGER.info("    3. HQL has better integration with Hibernate features");
        LOGGER.info("    4. JPQL ensures compatibility across JPA providers");
        
        LOGGER.info("  📊 In Practice:");
        LOGGER.info("    - Both use entity names instead of table names");
        LOGGER.info("    - Both support object-oriented queries");
        LOGGER.info("    - Both can use @Query annotation in Spring Data JPA");
        LOGGER.info("    - Native queries bypass both HQL and JPQL for direct SQL");
        
        // Demonstrate equivalent queries
        LOGGER.info("  🔍 Equivalent Query Examples:");
        
        // Both HQL and JPQL work the same for basic queries
        List<Employee> jpqlResult = employeeRepository.findByDepartmentNameHQL("Information Technology");
        LOGGER.info("    HQL/JPQL Query Result: Found {} IT employees", jpqlResult.size());
        
        // Show aggregate function usage (same in both)
        Double avgSalary = employeeRepository.getAverageSalaryHQL();
        LOGGER.info("    HQL/JPQL Aggregate: Average salary = ${:,.2f}", avgSalary);
    }
}
