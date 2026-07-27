package com.cognizant.hql.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Department Entity for HQL and Native Query demonstrations
 */
@Entity
@Table(name = "department")
@NamedQueries({
    @NamedQuery(
        name = "Department.findByNameNamed",
        query = "SELECT d FROM Department d WHERE d.name = :name"
    ),
    @NamedQuery(
        name = "Department.findWithEmployeeCountNamed",
        query = "SELECT d, SIZE(d.employees) FROM Department d"
    )
})
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true)
    @NotBlank(message = "Department name cannot be blank")
    private String name;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "budget")
    private Double budget;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "manager_name")
    private String managerName;
    
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Employee> employees = new ArrayList<>();
    
    // Constructors
    public Department() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Department(String name, String description, String location, Double budget, String managerName) {
        this();
        this.name = name;
        this.description = description;
        this.location = location;
        this.budget = budget;
        this.managerName = managerName;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public Double getBudget() {
        return budget;
    }
    
    public void setBudget(Double budget) {
        this.budget = budget;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getManagerName() {
        return managerName;
    }
    
    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", budget=" + budget +
                ", managerName='" + managerName + '\'' +
                ", employeeCount=" + (employees != null ? employees.size() : 0) +
                '}';
    }
}
