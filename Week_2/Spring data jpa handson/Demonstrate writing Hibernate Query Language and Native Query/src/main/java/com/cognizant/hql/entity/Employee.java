package com.cognizant.hql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Employee Entity for HQL and Native Query demonstrations
 */
@Entity
@Table(name = "employee")
@NamedQueries({
    @NamedQuery(
        name = "Employee.findByDepartmentNameNamed",
        query = "SELECT e FROM Employee e WHERE e.department.name = :departmentName"
    ),
    @NamedQuery(
        name = "Employee.findHighEarnersNamed", 
        query = "SELECT e FROM Employee e WHERE e.salary > :minSalary ORDER BY e.salary DESC"
    ),
    @NamedQuery(
        name = "Employee.findWithDepartmentFetchNamed",
        query = "SELECT e FROM Employee e JOIN FETCH e.department WHERE e.active = true"
    )
})
public class Employee {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    
    @Column(name = "email", nullable = false, unique = true)
    @NotBlank(message = "Email cannot be blank")
    private String email;
    
    @Column(name = "salary", precision = 10, scale = 2)
    @Positive(message = "Salary must be positive")
    private BigDecimal salary;
    
    @Column(name = "hire_date")
    @NotNull(message = "Hire date cannot be null")
    private LocalDate hireDate;
    
    @Column(name = "position")
    private String position;
    
    @Column(name = "age")
    @Positive(message = "Age must be positive")
    private Integer age;
    
    @Column(name = "active")
    private Boolean active = true;
    
    @Column(name = "experience_years")
    private Integer experienceYears;
    
    @Column(name = "phone")
    private String phone;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    
    // Constructors
    public Employee() {
    }
    
    public Employee(String firstName, String lastName, String email, BigDecimal salary, 
                   LocalDate hireDate, String position, Integer age, Boolean active, 
                   Integer experienceYears, String phone, Department department) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.hireDate = hireDate;
        this.position = position;
        this.age = age;
        this.active = active;
        this.experienceYears = experienceYears;
        this.phone = phone;
        this.department = department;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public BigDecimal getSalary() {
        return salary;
    }
    
    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }
    
    public LocalDate getHireDate() {
        return hireDate;
    }
    
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public Integer getAge() {
        return age;
    }
    
    public void setAge(Integer age) {
        this.age = age;
    }
    
    public Boolean getActive() {
        return active;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Integer getExperienceYears() {
        return experienceYears;
    }
    
    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Department getDepartment() {
        return department;
    }
    
    public void setDepartment(Department department) {
        this.department = department;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", hireDate=" + hireDate +
                ", age=" + age +
                ", active=" + active +
                ", experienceYears=" + experienceYears +
                ", department=" + (department != null ? department.getName() : "null") +
                '}';
    }
}
