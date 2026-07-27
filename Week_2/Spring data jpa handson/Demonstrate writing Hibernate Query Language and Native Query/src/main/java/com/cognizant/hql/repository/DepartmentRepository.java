package com.cognizant.hql.repository;

import com.cognizant.hql.entity.Department;
import com.cognizant.hql.dto.DepartmentSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface demonstrating various HQL, JPQL, and Native Query examples
 */
@Repository
@RepositoryRestResource(collectionResourceRel = "departments", path = "departments")
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    // ========== BASIC HQL/JPQL QUERIES ==========
    
    /**
     * Basic HQL query to find department by name
     * HQL uses entity names, not table names
     */
    @Query("SELECT d FROM Department d WHERE d.name = :name")
    Optional<Department> findByNameHQL(@Param("name") String name);
    
    /**
     * HQL query with LIKE operator for partial matching
     */
    @Query("SELECT d FROM Department d WHERE d.name LIKE %:name%")
    List<Department> findByNameContainingHQL(@Param("name") String name);
    
    /**
     * HQL query with multiple conditions using AND
     */
    @Query("SELECT d FROM Department d WHERE d.budget > :minBudget AND d.location = :location")
    List<Department> findByBudgetAndLocationHQL(@Param("minBudget") Double minBudget, @Param("location") String location);
    
    /**
     * HQL query with ORDER BY clause
     */
    @Query("SELECT d FROM Department d WHERE d.budget > :budget ORDER BY d.budget DESC, d.name ASC")
    List<Department> findByBudgetOrderedHQL(@Param("budget") Double budget);
    
    // ========== HQL FETCH KEYWORD DEMONSTRATIONS ==========
    
    /**
     * HQL with JOIN FETCH for eager loading of employees
     * FETCH keyword loads associated entities in a single query
     */
    @Query("SELECT DISTINCT d FROM Department d JOIN FETCH d.employees WHERE d.budget > :budget")
    List<Department> findDepartmentsWithEmployeesHQL(@Param("budget") Double budget);
    
    /**
     * HQL with LEFT JOIN FETCH to include departments even without employees
     */
    @Query("SELECT DISTINCT d FROM Department d LEFT JOIN FETCH d.employees ORDER BY d.name")
    List<Department> findAllDepartmentsWithEmployeesHQL();
    
    // ========== AGGREGATE FUNCTIONS IN HQL ==========
    
    /**
     * HQL COUNT function
     */
    @Query("SELECT COUNT(d) FROM Department d WHERE d.budget > :budget")
    Long countDepartmentsWithBudgetAboveHQL(@Param("budget") Double budget);
    
    /**
     * HQL SUM function
     */
    @Query("SELECT SUM(d.budget) FROM Department d")
    Double getTotalBudgetHQL();
    
    /**
     * HQL AVG function
     */
    @Query("SELECT AVG(d.budget) FROM Department d")
    Double getAverageBudgetHQL();
    
    /**
     * HQL MIN and MAX functions
     */
    @Query("SELECT MIN(d.budget), MAX(d.budget) FROM Department d")
    Object[] getMinMaxBudgetHQL();
    
    /**
     * HQL GROUP BY with aggregate functions
     */
    @Query("SELECT d.location, COUNT(d), AVG(d.budget) FROM Department d GROUP BY d.location")
    List<Object[]> getDepartmentStatsByLocationHQL();
    
    /**
     * HQL with SIZE function for collection size
     */
    @Query("SELECT d, SIZE(d.employees) FROM Department d")
    List<Object[]> getDepartmentsWithEmployeeCountHQL();
    
    /**
     * HQL HAVING clause with GROUP BY
     */
    @Query("SELECT d.location, COUNT(d) FROM Department d GROUP BY d.location HAVING COUNT(d) > :minCount")
    List<Object[]> getLocationsWithMinimumDepartmentsHQL(@Param("minCount") Long minCount);
    
    // ========== SUBQUERIES IN HQL ==========
    
    /**
     * HQL subquery to find departments with above-average budget
     */
    @Query("SELECT d FROM Department d WHERE d.budget > (SELECT AVG(d2.budget) FROM Department d2)")
    List<Department> findDepartmentsAboveAverageBudgetHQL();
    
    /**
     * HQL subquery with EXISTS
     */
    @Query("SELECT d FROM Department d WHERE EXISTS (SELECT e FROM Employee e WHERE e.department = d AND e.salary > :minSalary)")
    List<Department> findDepartmentsWithHighPaidEmployeesHQL(@Param("minSalary") Double minSalary);
    
    // ========== PROJECTION QUERIES ==========
    
    /**
     * HQL projection to select specific fields
     */
    @Query("SELECT d.name, d.budget, d.location FROM Department d WHERE d.budget > :budget")
    List<Object[]> getDepartmentSummaryHQL(@Param("budget") Double budget);
    
    /**
     * HQL projection with constructor expression (requires DTO)
     */
    @Query("SELECT new com.cognizant.hql.dto.DepartmentSummaryDTO(d.name, d.budget, d.location, SIZE(d.employees)) FROM Department d")
    List<DepartmentSummaryDTO> getDepartmentSummaryDTOHQL();
    
    // ========== NATIVE SQL QUERIES ==========
    
    /**
     * Native SQL query using table and column names
     * nativeQuery = true indicates this is a native SQL query
     */
    @Query(value = "SELECT * FROM department WHERE budget > :budget ORDER BY budget DESC", nativeQuery = true)
    List<Department> findByBudgetNative(@Param("budget") Double budget);
    
    /**
     * Native SQL query with JOIN
     */
    @Query(value = """
        SELECT d.*, COUNT(e.id) as employee_count 
        FROM department d 
        LEFT JOIN employee e ON d.id = e.department_id 
        GROUP BY d.id, d.name, d.description, d.location, d.budget, d.created_at, d.manager_name
        HAVING COUNT(e.id) > :minEmployees
        """, nativeQuery = true)
    List<Object[]> findDepartmentsWithMinimumEmployeesNative(@Param("minEmployees") Integer minEmployees);
    
    /**
     * Native SQL query for complex statistics
     */
    @Query(value = """
        SELECT 
            d.location,
            COUNT(d.id) as dept_count,
            AVG(d.budget) as avg_budget,
            SUM(d.budget) as total_budget,
            COUNT(e.id) as total_employees
        FROM department d 
        LEFT JOIN employee e ON d.id = e.department_id 
        GROUP BY d.location
        ORDER BY total_budget DESC
        """, nativeQuery = true)
    List<Object[]> getDepartmentStatisticsNative();
    
    /**
     * Native SQL query with window functions (H2 specific)
     */
    @Query(value = """
        SELECT 
            d.*,
            ROW_NUMBER() OVER (ORDER BY d.budget DESC) as budget_rank,
            RANK() OVER (PARTITION BY d.location ORDER BY d.budget DESC) as location_rank
        FROM department d
        """, nativeQuery = true)
    List<Object[]> getDepartmentsWithRankingNative();
    
    // ========== NAMED QUERIES ==========
    
    /**
     * Using named query defined in the entity
     */
    Optional<Department> findByNameNamed(@Param("name") String name);
    
    /**
     * Using named query for complex operations
     */
    List<Object[]> findWithEmployeeCountNamed();
    
    // ========== UPDATE AND DELETE QUERIES ==========
    
    /**
     * HQL UPDATE query
     */
    @Query("UPDATE Department d SET d.budget = d.budget * :multiplier WHERE d.location = :location")
    int updateBudgetByLocationHQL(@Param("multiplier") Double multiplier, @Param("location") String location);
    
    /**
     * Native SQL UPDATE query
     */
    @Query(value = "UPDATE department SET manager_name = :managerName WHERE location = :location", nativeQuery = true)
    int updateManagerByLocationNative(@Param("managerName") String managerName, @Param("location") String location);
}
