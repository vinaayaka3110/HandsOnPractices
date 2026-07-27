package com.cognizant.hql.dto;

import java.math.BigDecimal;

/**
 * DTO for Employee Summary projections
 * Used in HQL constructor expressions for projection queries
 */
public class EmployeeSummaryDTO {
    
    private String firstName;
    private String lastName;
    private String email;
    private BigDecimal salary;
    private String position;
    private String departmentName;
    
    // Constructor for HQL projection
    public EmployeeSummaryDTO(String firstName, String lastName, String email, 
                             BigDecimal salary, String position, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.salary = salary;
        this.position = position;
        this.departmentName = departmentName;
    }
    
    // Getters and Setters
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
    
    public String getPosition() {
        return position;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getDepartmentName() {
        return departmentName;
    }
    
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public String toString() {
        return "EmployeeSummaryDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", position='" + position + '\'' +
                ", departmentName='" + departmentName + '\'' +
                '}';
    }
}
