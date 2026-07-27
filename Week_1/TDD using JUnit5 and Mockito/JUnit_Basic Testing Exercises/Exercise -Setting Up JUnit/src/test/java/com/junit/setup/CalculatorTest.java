package com.junit.setup;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    
    private Calculator calculator = new Calculator();
    
    @Test
    public void testAdd() {
        int result = calculator.add(5, 3);
        assertEquals(8, result);
    }
    
    @Test
    public void testSubtract() {
        int result = calculator.subtract(10, 4);
        assertEquals(6, result);
    }
    
    @Test
    public void testMultiply() {
        int result = calculator.multiply(6, 7);
        assertEquals(42, result);
    }
    
    @Test
    public void testDivide() {
        int result = calculator.divide(15, 3);
        assertEquals(5, result);
    }
    
    @Test
    public void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(10, 0);
        });
    }
}
