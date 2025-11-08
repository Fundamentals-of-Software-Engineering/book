package dev.fose.cicd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for Calculator.
 * These tests will be automatically run in the CI/CD pipeline.
 */
@DisplayName("Calculator Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Addition should work correctly")
    void testAddition() {
        assertEquals(8, calculator.add(5, 3));
        assertEquals(0, calculator.add(-5, 5));
        assertEquals(-8, calculator.add(-5, -3));
    }

    @Test
    @DisplayName("Subtraction should work correctly")
    void testSubtraction() {
        assertEquals(2, calculator.subtract(5, 3));
        assertEquals(-10, calculator.subtract(-5, 5));
        assertEquals(-2, calculator.subtract(-5, -3));
    }

    @Test
    @DisplayName("Multiplication should work correctly")
    void testMultiplication() {
        assertEquals(15, calculator.multiply(5, 3));
        assertEquals(-25, calculator.multiply(-5, 5));
        assertEquals(15, calculator.multiply(-5, -3));
    }

    @Test
    @DisplayName("Division should work correctly")
    void testDivision() {
        assertEquals(1.666, calculator.divide(5, 3), 0.001);
        assertEquals(-1.0, calculator.divide(-5, 5), 0.001);
    }

    @Test
    @DisplayName("Division by zero should throw exception")
    void testDivisionByZero() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            calculator.divide(5, 0);
        });
        assertEquals("Cannot divide by zero", exception.getMessage());
    }
}
