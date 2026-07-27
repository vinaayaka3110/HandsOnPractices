package com.cognizant.hql.dto;

import java.math.BigDecimal;

/**
 * DTO for Department Summary projections
 * Used in HQL constructor expressions for projection queries
 */
public class DepartmentSummaryDTO {
    
    private String name;
    private Double budget;
    private String location;
    private Long employeeCount;
    
    // Constructor for HQL projection
    public DepartmentSummaryDTO(String name, Double budget, String location, Long employeeCount) {
        this.name = name;
        this.budget = budget;
        this.location = location;
        this.employeeCount = employeeCount;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Double getBudget() {
        return budget;
    }
    
    public void setBudget(Double budget) {
        this.budget = budget;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Long getEmployeeCount() {
        return employeeCount;
    }
    
    public void setEmployeeCount(Long employeeCount) {
        this.employeeCount = employeeCount;
    }
    
    @Override
    public String toString() {
        return "DepartmentSummaryDTO{" +
                "name='" + name + '\'' +
                ", budget=" + budget +
                ", location='" + location + '\'' +
                ", employeeCount=" + employeeCount +
                '}';
    }
}
