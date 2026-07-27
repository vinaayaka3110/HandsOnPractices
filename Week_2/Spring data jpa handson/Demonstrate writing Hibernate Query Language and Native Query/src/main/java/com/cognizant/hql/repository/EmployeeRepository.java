package com.cognizant.hql.repository;

import com.cognizant.hql.entity.Employee;
import com.cognizant.hql.dto.EmployeeSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface demonstrating Employee-specific HQL, JPQL, and Native Queries
 */
@Repository
@RepositoryRestResource(collectionResourceRel = "employees", path = "employees")
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    // ========== BASIC HQL/JPQL QUERIES ==========
    
    /**
     * HQL query to find employee by email
     */
    @Query("SELECT e FROM Employee e WHERE e.email = :email")
    Optional<Employee> findByEmailHQL(@Param("email") String email);
    
    /**
     * HQL query with JOIN to navigate relationships
     */
    @Query("SELECT e FROM Employee e JOIN e.department d WHERE d.name = :departmentName")
    List<Employee> findByDepartmentNameHQL(@Param("departmentName") String departmentName);
    
    /**
     * HQL query with multiple JOINs and conditions
     */
    @Query("SELECT e FROM Employee e JOIN e.department d WHERE d.location = :location AND e.active = true")
    List<Employee> findActiveEmployeesByLocationHQL(@Param("location") String location);
    
    // ========== HQL FETCH KEYWORD EXAMPLES ==========
    
    /**
     * HQL with JOIN FETCH to eagerly load department
     * Avoids N+1 query problem
     */
    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.salary > :minSalary")
    List<Employee> findHighEarnersWithDepartmentHQL(@Param("minSalary") BigDecimal minSalary);
    
    /**
     * HQL with multiple FETCH JOINs
     */
    @Query("SELECT DISTINCT e FROM Employee e JOIN FETCH e.department d WHERE d.budget > :budget")
    List<Employee> findEmployeesInWellFundedDepartmentsHQL(@Param("budget") Double budget);
    
    // ========== AGGREGATE FUNCTIONS IN HQL ==========
    
    /**
     * HQL COUNT with conditions
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.name = :departmentName AND e.active = true")
    Long countActiveEmployeesByDepartmentHQL(@Param("departmentName") String departmentName);
    
    /**
     * HQL SUM for salary calculations
     */
    @Query("SELECT SUM(e.salary) FROM Employee e WHERE e.department.name = :departmentName")
    BigDecimal getTotalSalaryByDepartmentHQL(@Param("departmentName") String departmentName);
    
    /**
     * HQL AVG for average salary
     */
    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.active = true")
    Double getAverageSalaryHQL();
    
    /**
     * HQL MIN and MAX salary
     */
    @Query("SELECT MIN(e.salary), MAX(e.salary) FROM Employee e WHERE e.department.name = :departmentName")
    Object[] getMinMaxSalaryByDepartmentHQL(@Param("departmentName") String departmentName);
    
    /**
     * HQL GROUP BY with aggregate functions
     */
    @Query("SELECT e.department.name, COUNT(e), AVG(e.salary), SUM(e.salary) FROM Employee e GROUP BY e.department.name")
    List<Object[]> getSalaryStatsByDepartmentHQL();
    
    /**
     * HQL GROUP BY with HAVING clause
     */
    @Query("SELECT e.position, COUNT(e), AVG(e.salary) FROM Employee e GROUP BY e.position HAVING COUNT(e) > :minCount")
    List<Object[]> getPositionStatsWithMinimumCountHQL(@Param("minCount") Long minCount);
    
    // ========== ADVANCED HQL QUERIES ==========
    
    /**
     * HQL with CASE WHEN for conditional logic
     */
    @Query("""
        SELECT e.firstName, e.lastName, e.salary,
               CASE 
                   WHEN e.salary > 80000 THEN 'High'
                   WHEN e.salary > 50000 THEN 'Medium'
                   ELSE 'Low'
               END
        FROM Employee e
        WHERE e.active = true
        ORDER BY e.salary DESC
        """)
    List<Object[]> getEmployeesWithSalaryCategorizationHQL();
    
    /**
     * HQL subquery to find employees earning above department average
     */
    @Query("""
        SELECT e FROM Employee e 
        WHERE e.salary > (
            SELECT AVG(e2.salary) 
            FROM Employee e2 
            WHERE e2.department = e.department
        )
        """)
    List<Employee> findEmployeesAboveDepartmentAverageHQL();
    
    /**
     * HQL with EXISTS subquery
     */
    @Query("""
        SELECT e FROM Employee e 
        WHERE EXISTS (
            SELECT 1 FROM Employee e2 
            WHERE e2.department = e.department 
            AND e2.salary > :threshold
        )
        """)
    List<Employee> findEmployeesInDepartmentsWithHighEarnersHQL(@Param("threshold") BigDecimal threshold);
    
    /**
     * HQL with date functions
     */
    @Query("SELECT e FROM Employee e WHERE YEAR(e.hireDate) = :year")
    List<Employee> findEmployeesHiredInYearHQL(@Param("year") Integer year);
    
    /**
     * HQL with string functions
     */
    @Query("SELECT e FROM Employee e WHERE UPPER(e.firstName) LIKE UPPER(:pattern)")
    List<Employee> findByFirstNamePatternHQL(@Param("pattern") String pattern);
    
    // ========== PROJECTION QUERIES ==========
    
    /**
     * HQL projection for specific fields
     */
    @Query("SELECT e.firstName, e.lastName, e.email, e.salary, e.department.name FROM Employee e WHERE e.active = true")
    List<Object[]> getActiveEmployeeSummaryHQL();
    
    /**
     * HQL projection with constructor (requires DTO)
     */
    @Query("""
        SELECT new com.cognizant.hql.dto.EmployeeSummaryDTO(
            e.firstName, e.lastName, e.email, e.salary, e.position, e.department.name
        ) 
        FROM Employee e WHERE e.active = true
        """)
    List<EmployeeSummaryDTO> getActiveEmployeeSummaryDTOHQL();
    
    // ========== NATIVE SQL QUERIES ==========
    
    /**
     * Native SQL query with table joins
     */
    @Query(value = """
        SELECT e.*, d.name as department_name, d.location as department_location
        FROM employee e 
        JOIN department d ON e.department_id = d.id
        WHERE e.salary > :minSalary
        ORDER BY e.salary DESC
        """, nativeQuery = true)
    List<Object[]> findHighEarnersWithDepartmentInfoNative(@Param("minSalary") BigDecimal minSalary);
    
    /**
     * Native SQL query with window functions
     */
    @Query(value = """
        SELECT 
            e.*,
            RANK() OVER (PARTITION BY e.department_id ORDER BY e.salary DESC) as salary_rank,
            ROW_NUMBER() OVER (ORDER BY e.hire_date) as hire_order
        FROM employee e
        WHERE e.active = true
        """, nativeQuery = true)
    List<Object[]> getEmployeesWithRankingNative();
    
    /**
     * Native SQL query for complex analytics
     */
    @Query(value = """
        SELECT 
            d.name as department,
            COUNT(e.id) as employee_count,
            AVG(e.salary) as avg_salary,
            MIN(e.salary) as min_salary,
            MAX(e.salary) as max_salary,
            SUM(e.salary) as total_salary,
            AVG(e.age) as avg_age,
            AVG(e.experience_years) as avg_experience
        FROM employee e
        JOIN department d ON e.department_id = d.id
        WHERE e.active = true
        GROUP BY d.id, d.name
        ORDER BY avg_salary DESC
        """, nativeQuery = true)
    List<Object[]> getDepartmentEmployeeAnalyticsNative();
    
    /**
     * Native SQL with date functions
     */
    @Query(value = """
        SELECT 
            EXTRACT(YEAR FROM e.hire_date) as hire_year,
            COUNT(*) as employees_hired,
            AVG(e.salary) as avg_salary
        FROM employee e
        WHERE e.active = true
        GROUP BY EXTRACT(YEAR FROM e.hire_date)
        ORDER BY hire_year DESC
        """, nativeQuery = true)
    List<Object[]> getHiringTrendsNative();
    
    // ========== NAMED QUERIES ==========
    
    /**
     * Using named query from entity
     */
    List<Employee> findByDepartmentNameNamed(@Param("departmentName") String departmentName);
    
    /**
     * Using named query for high earners
     */
    List<Employee> findHighEarnersNamed(@Param("minSalary") BigDecimal minSalary);
    
    /**
     * Using named query with fetch
     */
    List<Employee> findWithDepartmentFetchNamed();
    
    // ========== UPDATE AND DELETE QUERIES ==========
    
    /**
     * HQL bulk update query
     */
    @Query("UPDATE Employee e SET e.salary = e.salary * :multiplier WHERE e.department.name = :departmentName")
    int updateSalaryByDepartmentHQL(@Param("multiplier") Double multiplier, @Param("departmentName") String departmentName);
    
    /**
     * HQL conditional update
     */
    @Query("UPDATE Employee e SET e.active = false WHERE e.hireDate < :cutoffDate")
    int deactivateOldEmployeesHQL(@Param("cutoffDate") LocalDate cutoffDate);
    
    /**
     * Native SQL update with complex conditions
     */
    @Query(value = """
        UPDATE employee 
        SET experience_years = EXTRACT(YEAR FROM CURRENT_DATE) - EXTRACT(YEAR FROM hire_date)
        WHERE experience_years IS NULL OR experience_years = 0
        """, nativeQuery = true)
    int updateExperienceYearsNative();
}
