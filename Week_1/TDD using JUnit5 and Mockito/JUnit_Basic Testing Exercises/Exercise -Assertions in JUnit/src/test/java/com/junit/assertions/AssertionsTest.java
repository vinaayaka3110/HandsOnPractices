package com.junit.assertions;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

/**
 * Comprehensive JUnit assertions test class
 * Demonstrates all major JUnit assertion methods
 */
public class AssertionsTest {
    
    private Student student;
    private List<String> subjects;
    
    @Before
    public void setUp() {
        System.out.println("Setting up AssertionsTest...");
        student = new Student("John Doe", 20);
        subjects = Arrays.asList("Math", "Science", "English");
    }
    
    @After
    public void tearDown() {
        student = null;
        subjects = null;
        System.out.println("AssertionsTest completed.\n");
    }
    
    /**
     * Basic assertions test - as provided in the solution
     */
    @Test
    public void testAssertions() {
        System.out.println("Testing basic assertions");
        
        // Assert equals
        assertEquals(5, 2 + 3);
        
        // Assert true
        assertTrue(5 > 3);
        
        // Assert false
        assertFalse(5 < 3);
        
        // Assert null
        assertNull(null);
        
        // Assert not null
        assertNotNull(new Object());
        
        System.out.println("Basic assertions passed!");
    }
    
    /**
     * Numeric assertions with different data types
     */
    @Test
    public void testNumericAssertions() {
        System.out.println("Testing numeric assertions");
        
        // Integer assertions
        assertEquals("Addition should work", 10, 7 + 3);
        assertEquals("Multiplication should work", 15, 3 * 5);
        
        // Long assertions
        assertEquals(100L, 50L + 50L);
        
        // Double assertions with delta (important for floating point comparison)
        assertEquals("Double comparison with delta", 3.14159, Math.PI, 0.0001);
        assertEquals("Division result", 2.5, 5.0 / 2.0, 0.001);
        
        // Float assertions
        assertEquals(1.5f, 3.0f / 2.0f, 0.001f);
        
        System.out.println("Numeric assertions passed!");
    }
    
    /**
     * String assertions
     */
    @Test
    public void testStringAssertions() {
        System.out.println("Testing string assertions");
        
        String actual = "Hello World";
        String expected = "Hello World";
        String nullString = null;
        String emptyString = "";
        
        // String equality
        assertEquals("Strings should be equal", expected, actual);
        assertEquals("String concatenation", "Hello World", "Hello" + " " + "World");
        
        // Null and not null
        assertNull("Null string should be null", nullString);
        assertNotNull("Non-null string should not be null", actual);
        
        // True/False assertions on string operations
        assertTrue("String should start with 'Hello'", actual.startsWith("Hello"));
        assertTrue("String should end with 'World'", actual.endsWith("World"));
        assertTrue("String should contain 'lo Wo'", actual.contains("lo Wo"));
        assertFalse("String should not be empty", actual.isEmpty());
        assertTrue("Empty string should be empty", emptyString.isEmpty());
        
        System.out.println("String assertions passed!");
    }
    
    /**
     * Object assertions using Student class
     */
    @Test
    public void testObjectAssertions() {
        System.out.println("Testing object assertions");
        
        // Object not null
        assertNotNull("Student object should not be null", student);
        
        // Object properties
        assertEquals("Student name should match", "John Doe", student.getName());
        assertEquals("Student age should match", 20, student.getAge());
        assertTrue("Student should be active by default", student.isActive());
        assertTrue("Student should be adult", student.isAdult());
        
        // Object state changes
        student.setActive(false);
        assertFalse("Student should be inactive after setting", student.isActive());
        
        student.addSubject("Mathematics");
        assertEquals("Subject count should be 1", 1, student.getSubjectCount());
        
        // Object equality
        Student anotherStudent = new Student("John Doe", 20);
        assertEquals("Students with same data should be equal", student.getName(), anotherStudent.getName());
        assertEquals("Students should have same age", student.getAge(), anotherStudent.getAge());
        
        System.out.println("Object assertions passed!");
    }
    
    /**
     * Collection and array assertions
     */
    @Test
    public void testCollectionAssertions() {
        System.out.println("Testing collection assertions");
        
        List<String> actualSubjects = Arrays.asList("Math", "Science", "English");
        List<String> expectedSubjects = Arrays.asList("Math", "Science", "English");
        List<String> emptyList = Arrays.asList();
        
        // List equality
        assertEquals("Lists should be equal", expectedSubjects, actualSubjects);
        
        // List size
        assertEquals("List should have 3 elements", 3, actualSubjects.size());
        assertEquals("Empty list should have 0 elements", 0, emptyList.size());
        
        // List contents
        assertTrue("List should contain Math", actualSubjects.contains("Math"));
        assertTrue("List should contain Science", actualSubjects.contains("Science"));
        assertFalse("List should not contain History", actualSubjects.contains("History"));
        
        // Array assertions
        String[] subjectArray = {"Math", "Science", "English"};
        String[] expectedArray = {"Math", "Science", "English"};
        
        assertArrayEquals("Arrays should be equal", expectedArray, subjectArray);
        assertEquals("Array length should be 3", 3, subjectArray.length);
        assertEquals("First element should be Math", "Math", subjectArray[0]);
        
        System.out.println("Collection assertions passed!");
    }
    
    /**
     * Boolean and condition assertions
     */
    @Test
    public void testBooleanAssertions() {
        System.out.println("Testing boolean assertions");
        
        boolean isTrue = true;
        boolean isFalse = false;
        
        // Direct boolean tests
        assertTrue("Should be true", isTrue);
        assertFalse("Should be false", isFalse);
        
        // Condition-based boolean tests
        assertTrue("20 should be greater than 18", student.getAge() > 18);
        assertTrue("Student should be adult", student.isAdult());
        assertFalse("Age should not be negative", student.getAge() < 0);
        
        // Complex conditions
        assertTrue("Student should be adult and active", 
                   student.isAdult() && student.isActive());
        
        // String boolean operations
        String testString = "JUnit Testing";
        assertTrue("String should not be null or empty", 
                   testString != null && !testString.isEmpty());
        assertTrue("String should contain 'Unit'", testString.contains("Unit"));
        
        System.out.println("Boolean assertions passed!");
    }
    
    /**
     * Exception and error assertions
     */
    @Test
    public void testExceptionAssertions() {
        System.out.println("Testing scenarios that should NOT throw exceptions");
        
        // These operations should work without throwing exceptions
        student.setName("Jane Smith");
        assertEquals("Name should be updated", "Jane Smith", student.getName());
        
        student.setAge(25);
        assertEquals("Age should be updated", 25, student.getAge());
        
        // Adding valid subject should not throw exception
        student.addSubject("Physics");
        assertEquals("Should have 1 subject", 1, student.getSubjectCount());
        
        // These assertions verify normal operation
        assertNotNull("Student name should not be null", student.getName());
        assertTrue("Age should be positive", student.getAge() > 0);
        
        System.out.println("Exception assertions passed!");
    }
    
    /**
     * Edge case assertions
     */
    @Test
    public void testEdgeCaseAssertions() {
        System.out.println("Testing edge cases");
        
        // Boundary values
        Student youngStudent = new Student("Alice", 17);
        Student adultStudent = new Student("Bob", 18);
        
        assertFalse("17 year old should not be adult", youngStudent.isAdult());
        assertTrue("18 year old should be adult", adultStudent.isAdult());
        
        // Empty and null handling
        Student emptyStudent = new Student();
        assertNull("Empty student should have null name", emptyStudent.getName());
        assertEquals("Empty student should have age 0", 0, emptyStudent.getAge());
        assertEquals("Empty student should have no subjects", 0, emptyStudent.getSubjectCount());
        
        // Adding null subject should not change count
        emptyStudent.addSubject(null);
        assertEquals("Adding null subject should not change count", 0, emptyStudent.getSubjectCount());
        
        // Adding empty subject should not change count
        emptyStudent.addSubject("");
        assertEquals("Adding empty subject should not change count", 0, emptyStudent.getSubjectCount());
        
        // Adding whitespace-only subject should not change count
        emptyStudent.addSubject("   ");
        assertEquals("Adding whitespace subject should not change count", 0, emptyStudent.getSubjectCount());
        
        System.out.println("Edge case assertions passed!");
    }
    
    /**
     * Comprehensive assertion demonstration
     */
    @Test
    public void testComprehensiveAssertions() {
        System.out.println("Testing comprehensive assertion scenarios");
        
        // Create and populate a student
        Student testStudent = new Student("Test Student", 22);
        testStudent.addSubject("Computer Science");
        testStudent.addSubject("Mathematics");
        testStudent.addSubject("Physics");
        
        // Multiple assertions on the same object
        assertNotNull("Student should exist", testStudent);
        assertEquals("Name assertion", "Test Student", testStudent.getName());
        assertEquals("Age assertion", 22, testStudent.getAge());
        assertEquals("Subject count assertion", 3, testStudent.getSubjectCount());
        assertTrue("Adult assertion", testStudent.isAdult());
        assertTrue("Active assertion", testStudent.isActive());
        
        // String operations
        String info = testStudent.getFullInfo();
        assertTrue("Info should contain name", info.contains("Test Student"));
        assertTrue("Info should contain age", info.contains("22"));
        assertTrue("Info should contain subject count", info.contains("3"));
        
        // State change assertions
        testStudent.setActive(false);
        assertFalse("Should be inactive after change", testStudent.isActive());
        
        testStudent.setAge(16);
        assertFalse("Should not be adult after age change", testStudent.isAdult());
        assertEquals("Age should be updated", 16, testStudent.getAge());
        
        System.out.println("Comprehensive assertions passed!");
    }
}
