package com.cognizant.hql.config;

import com.cognizant.hql.entity.Department;
import com.cognizant.hql.entity.Employee;
import com.cognizant.hql.repository.DepartmentRepository;
import com.cognizant.hql.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Data initializer to populate the database with sample data
 */
@Component
public class DataInitializer implements CommandLineRunner {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DataInitializer.class);
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Override
    public void run(String... args) throws Exception {
        LOGGER.info("Initializing sample data for HQL demonstrations...");
        
        // Check if data already exists
        if (departmentRepository.count() > 0) {
            LOGGER.info("Data already exists, skipping initialization");
            return;
        }
        
        // Create departments
        Department itDept = new Department();
        itDept.setName("Information Technology");
        itDept.setLocation("New York");
        itDept.setBudget(500000.0);
        departmentRepository.save(itDept);
        
        Department hrDept = new Department();
        hrDept.setName("Human Resources");
        hrDept.setLocation("Chicago");
        hrDept.setBudget(200000.0);
        departmentRepository.save(hrDept);
        
        Department financeDept = new Department();
        financeDept.setName("Finance");
        financeDept.setLocation("Boston");
        financeDept.setBudget(300000.0);
        departmentRepository.save(financeDept);
        
        Department marketingDept = new Department();
        marketingDept.setName("Marketing");
        marketingDept.setLocation("San Francisco");
        marketingDept.setBudget(250000.0);
        departmentRepository.save(marketingDept);
        
        Department salesDept = new Department();
        salesDept.setName("Sales");
        salesDept.setLocation("Los Angeles");
        salesDept.setBudget(400000.0);
        departmentRepository.save(salesDept);
        
        // Create employees for IT Department
        Employee emp1 = new Employee();
        emp1.setFirstName("John");
        emp1.setLastName("Smith");
        emp1.setEmail("john.smith@company.com");
        emp1.setSalary(new BigDecimal("75000"));
        emp1.setHireDate(LocalDate.of(2020, 1, 15));
        emp1.setPosition("Senior Developer");
        emp1.setActive(true);
        emp1.setDepartment(itDept);
        employeeRepository.save(emp1);
        
        Employee emp2 = new Employee();
        emp2.setFirstName("Jane");
        emp2.setLastName("Doe");
        emp2.setEmail("jane.doe@company.com");
        emp2.setSalary(new BigDecimal("85000"));
        emp2.setHireDate(LocalDate.of(2019, 3, 10));
        emp2.setPosition("Team Lead");
        emp2.setActive(true);
        emp2.setDepartment(itDept);
        employeeRepository.save(emp2);
        
        Employee emp3 = new Employee();
        emp3.setFirstName("Mike");
        emp3.setLastName("Johnson");
        emp3.setEmail("mike.johnson@company.com");
        emp3.setSalary(new BigDecimal("65000"));
        emp3.setHireDate(LocalDate.of(2021, 6, 20));
        emp3.setPosition("Junior Developer");
        emp3.setActive(true);
        emp3.setDepartment(itDept);
        employeeRepository.save(emp3);
        
        // Create employees for HR Department
        Employee emp4 = new Employee();
        emp4.setFirstName("Sarah");
        emp4.setLastName("Wilson");
        emp4.setEmail("sarah.wilson@company.com");
        emp4.setSalary(new BigDecimal("60000"));
        emp4.setHireDate(LocalDate.of(2018, 9, 5));
        emp4.setPosition("HR Manager");
        emp4.setActive(true);
        emp4.setDepartment(hrDept);
        employeeRepository.save(emp4);
        
        Employee emp5 = new Employee();
        emp5.setFirstName("David");
        emp5.setLastName("Brown");
        emp5.setEmail("david.brown@company.com");
        emp5.setSalary(new BigDecimal("50000"));
        emp5.setHireDate(LocalDate.of(2020, 11, 12));
        emp5.setPosition("HR Specialist");
        emp5.setActive(true);
        emp5.setDepartment(hrDept);
        employeeRepository.save(emp5);
        
        // Create employees for Finance Department
        Employee emp6 = new Employee();
        emp6.setFirstName("Lisa");
        emp6.setLastName("Davis");
        emp6.setEmail("lisa.davis@company.com");
        emp6.setSalary(new BigDecimal("70000"));
        emp6.setHireDate(LocalDate.of(2017, 4, 8));
        emp6.setPosition("Financial Analyst");
        emp6.setActive(true);
        emp6.setDepartment(financeDept);
        employeeRepository.save(emp6);
        
        Employee emp7 = new Employee();
        emp7.setFirstName("Robert");
        emp7.setLastName("Miller");
        emp7.setEmail("robert.miller@company.com");
        emp7.setSalary(new BigDecimal("90000"));
        emp7.setHireDate(LocalDate.of(2016, 2, 18));
        emp7.setPosition("Finance Manager");
        emp7.setActive(true);
        emp7.setDepartment(financeDept);
        employeeRepository.save(emp7);
        
        // Create employees for Marketing Department
        Employee emp8 = new Employee();
        emp8.setFirstName("Emily");
        emp8.setLastName("Taylor");
        emp8.setEmail("emily.taylor@company.com");
        emp8.setSalary(new BigDecimal("55000"));
        emp8.setHireDate(LocalDate.of(2021, 8, 25));
        emp8.setPosition("Marketing Coordinator");
        emp8.setActive(true);
        emp8.setDepartment(marketingDept);
        employeeRepository.save(emp8);
        
        Employee emp9 = new Employee();
        emp9.setFirstName("Chris");
        emp9.setLastName("Anderson");
        emp9.setEmail("chris.anderson@company.com");
        emp9.setSalary(new BigDecimal("80000"));
        emp9.setHireDate(LocalDate.of(2019, 7, 14));
        emp9.setPosition("Marketing Manager");
        emp9.setActive(true);
        emp9.setDepartment(marketingDept);
        employeeRepository.save(emp9);
        
        // Create employees for Sales Department
        Employee emp10 = new Employee();
        emp10.setFirstName("Jessica");
        emp10.setLastName("Garcia");
        emp10.setEmail("jessica.garcia@company.com");
        emp10.setSalary(new BigDecimal("65000"));
        emp10.setHireDate(LocalDate.of(2020, 5, 30));
        emp10.setPosition("Sales Representative");
        emp10.setActive(true);
        emp10.setDepartment(salesDept);
        employeeRepository.save(emp10);
        
        Employee emp11 = new Employee();
        emp11.setFirstName("Mark");
        emp11.setLastName("Rodriguez");
        emp11.setEmail("mark.rodriguez@company.com");
        emp11.setSalary(new BigDecimal("95000"));
        emp11.setHireDate(LocalDate.of(2015, 12, 3));
        emp11.setPosition("Sales Manager");
        emp11.setActive(true);
        emp11.setDepartment(salesDept);
        employeeRepository.save(emp11);
        
        // Create one inactive employee for testing
        Employee emp12 = new Employee();
        emp12.setFirstName("Former");
        emp12.setLastName("Employee");
        emp12.setEmail("former.employee@company.com");
        emp12.setSalary(new BigDecimal("45000"));
        emp12.setHireDate(LocalDate.of(2018, 1, 1));
        emp12.setPosition("Ex-Employee");
        emp12.setActive(false);
        emp12.setDepartment(itDept);
        employeeRepository.save(emp12);
        
        LOGGER.info("Sample data initialization completed!");
        LOGGER.info("Created {} departments and {} employees", 
                    departmentRepository.count(), employeeRepository.count());
    }
}
