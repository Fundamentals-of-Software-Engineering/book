package com.fose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD Example: Calculator Test Suite
 *
 * This test suite demonstrates Test-Driven Development (TDD) methodology.
 * Following the "red-green-refactor" cycle described in the book:
 *
 * 1. RED: Write a failing test
 * 2. GREEN: Write minimum code to pass the test
 * 3. REFACTOR: Improve code without changing behavior
 *
 * Each test below represents the RED phase. The corresponding implementation
 * in Calculator.java shows the GREEN and REFACTOR phases.
 */
@DisplayName("Calculator TDD Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    // ========== TDD CYCLE 1: Addition ==========

    @Test
    @DisplayName("[TDD Cycle 1 - RED] Should add two positive numbers")
    void shouldAddTwoPositiveNumbers() {
        // RED: This test was written FIRST, before any code existed
        // It failed because add() didn't exist yet
        assertEquals(5, calculator.add(2, 3));
        // GREEN: Then implemented add() { return a + b; }
        // REFACTOR: No refactoring needed
    }

    @Test
    @DisplayName("[TDD Cycle 1 - Additional] Should add negative numbers")
    void shouldAddNegativeNumbers() {
        assertEquals(0, calculator.add(-1, 1));
        assertEquals(-5, calculator.add(-2, -3));
    }

    // ========== TDD CYCLE 2: Subtraction ==========

    @Test
    @DisplayName("[TDD Cycle 2 - RED] Should subtract two numbers")
    void shouldSubtractTwoNumbers() {
        // RED: Wrote this test first, it failed
        assertEquals(2, calculator.subtract(5, 3));
        // GREEN: Implemented subtract() { return a - b; }
        // REFACTOR: No refactoring needed
    }

    @Test
    @DisplayName("[TDD Cycle 2 - Additional] Should handle negative results")
    void shouldHandleNegativeSubtractionResults() {
        assertEquals(-2, calculator.subtract(3, 5));
    }

    // ========== TDD CYCLE 3: Multiplication ==========

    @Test
    @DisplayName("[TDD Cycle 3 - RED] Should multiply two numbers")
    void shouldMultiplyTwoNumbers() {
        // RED: Test written first
        assertEquals(12, calculator.multiply(4, 3));
        // GREEN: Implemented multiply() { return a * b; }
        // REFACTOR: No refactoring needed
    }

    @Test
    @DisplayName("[TDD Cycle 3 - Additional] Should handle multiplication by zero")
    void shouldHandleMultiplicationByZero() {
        assertEquals(0, calculator.multiply(5, 0));
        assertEquals(0, calculator.multiply(0, 5));
    }

    @Test
    @DisplayName("[TDD Cycle 3 - Additional] Should handle negative multiplication")
    void shouldHandleNegativeMultiplication() {
        assertEquals(-12, calculator.multiply(4, -3));
        assertEquals(12, calculator.multiply(-4, -3));
    }

    // ========== TDD CYCLE 4: Division ==========

    @Test
    @DisplayName("[TDD Cycle 4 - RED] Should divide two numbers")
    void shouldDivideTwoNumbers() {
        // RED: Test written first, failed
        assertEquals(4, calculator.divide(8, 2));
        // GREEN: Implemented divide() { return a / b; }
        // Note: This led to next test for edge case
    }

    @Test
    @DisplayName("[TDD Cycle 4 - RED] Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        // RED: Wrote this test after realizing zero check was needed
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calculator.divide(5, 0)
        );
        // GREEN: Added if (b == 0) throw exception
        // REFACTOR: Extracted validateDivisor() method for clarity

        assertTrue(exception.getMessage().contains("Division by zero"));
    }

    @Test
    @DisplayName("[TDD Cycle 4 - Additional] Should handle negative division")
    void shouldHandleNegativeDivision() {
        assertEquals(-2, calculator.divide(6, -3));
        assertEquals(2, calculator.divide(-6, -3));
    }

    // ========== TDD CYCLE 5: Power ==========

    @Test
    @DisplayName("[TDD Cycle 5 - RED] Should calculate power correctly")
    void shouldCalculatePowerCorrectly() {
        // RED: Test written first
        assertEquals(8, calculator.power(2, 3));
        assertEquals(27, calculator.power(3, 3));
        // GREEN: Implemented using loop
        // REFACTOR: Added edge case handling
    }

    @Test
    @DisplayName("[TDD Cycle 5 - RED] Should handle power of zero")
    void shouldHandlePowerOfZero() {
        // RED: Edge case test
        assertEquals(1, calculator.power(5, 0));
        assertEquals(1, calculator.power(0, 0)); // Mathematical convention
        // GREEN: Added if (exponent == 0) return 1
    }

    @Test
    @DisplayName("[TDD Cycle 5 - RED] Should handle power of one")
    void shouldHandlePowerOfOne() {
        // RED: Another edge case
        assertEquals(5, calculator.power(5, 1));
        // GREEN: Already handled by loop implementation
    }

    @Test
    @DisplayName("[TDD Cycle 5 - RED] Should throw exception for negative exponents")
    void shouldThrowExceptionForNegativeExponents() {
        // RED: Edge case for negative exponents
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> calculator.power(2, -1)
        );
        // GREEN: Added validation for negative exponents
        // REFACTOR: Clear error message

        assertTrue(exception.getMessage().contains("Negative exponents"));
    }

    /*
     * TDD WORKFLOW SUMMARY:
     *
     * For each feature (add, subtract, multiply, divide, power):
     *
     * 1. RED Phase:
     *    - Write a test that FAILS (method doesn't exist yet)
     *    - Run tests, watch it FAIL (red bar in IDE)
     *    - Think about edge cases, write tests for those too
     *
     * 2. GREEN Phase:
     *    - Write MINIMUM code to make test pass
     *    - Don't over-engineer, just make it work
     *    - Run tests, watch them PASS (green bar in IDE)
     *
     * 3. REFACTOR Phase:
     *    - Clean up code while keeping tests green
     *    - Extract methods, improve names, remove duplication
     *    - Run tests after each refactoring to ensure nothing broke
     *
     * BENEFITS OF TDD:
     * - Forces you to think about requirements before coding
     * - Creates a safety net for refactoring
     * - Results in testable code by design
     * - Provides living documentation
     * - Catches bugs early
     *
     * This aligns with the book's description:
     * "Test-first methodologies, such as test-driven development (TDD),
     *  follow a 'red-green-refactor' cycle."
     */
}
