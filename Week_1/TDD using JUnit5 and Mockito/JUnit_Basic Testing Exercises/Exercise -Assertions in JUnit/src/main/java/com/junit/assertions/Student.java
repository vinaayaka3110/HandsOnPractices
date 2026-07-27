package com.junit.assertions;

import java.util.ArrayList;
import java.util.List;

/**
 * A sample class to demonstrate various JUnit assertions
 */
public class Student {
    private String name;
    private int age;
    private List<String> subjects;
    private boolean active;
    
    public Student() {
        this.subjects = new ArrayList<>();
        this.active = true;
    }
    
    public Student(String name, int age) {
        this();
        this.name = name;
        this.age = age;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public List<String> getSubjects() {
        return subjects;
    }
    
    public void addSubject(String subject) {
        if (subject != null && !subject.trim().isEmpty()) {
            this.subjects.add(subject);
        }
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isAdult() {
        return age >= 18;
    }
    
    public int getSubjectCount() {
        return subjects.size();
    }
    
    public String getFullInfo() {
        return String.format("Student{name='%s', age=%d, subjects=%d, active=%s}", 
                           name, age, subjects.size(), active);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Student student = (Student) obj;
        return age == student.age && 
               active == student.active &&
               (name != null ? name.equals(student.name) : student.name == null) &&
               subjects.equals(student.subjects);
    }
    
    @Override
    public String toString() {
        return getFullInfo();
    }
}
