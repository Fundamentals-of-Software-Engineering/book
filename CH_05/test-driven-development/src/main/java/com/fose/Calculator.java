package com.fose;

/**
 * Calculator built using Test-Driven Development (TDD).
 *
 * This class was developed following the TDD "red-green-refactor" cycle:
 * 1. RED: Write a failing test
 * 2. GREEN: Write minimum code to make the test pass
 * 3. REFACTOR: Improve the code while keeping tests green
 *
 * See CalculatorTest.java for the test-first approach and
 * README.md for the complete TDD workflow.
 */
public class Calculator {

    /**
     * TDD Cycle 1: Addition
     * - RED: Wrote test for add(2, 3) expecting 5
     * - GREEN: Implemented simple addition
     * - REFACTOR: No refactoring needed for simple implementation
     */
    public int add(int a, int b) {
        return a + b;
    }

    /**
     * TDD Cycle 2: Subtraction
     * - RED: Wrote test for subtract(5, 3) expecting 2
     * - GREEN: Implemented simple subtraction
     * - REFACTOR: No refactoring needed
     */
    public int subtract(int a, int b) {
        return a - b;
    }

    /**
     * TDD Cycle 3: Multiplication
     * - RED: Wrote test for multiply(4, 3) expecting 12
     * - GREEN: Implemented simple multiplication
     * - REFACTOR: No refactoring needed
     */
    public int multiply(int a, int b) {
        return a * b;
    }

    /**
     * TDD Cycle 4: Division
     * - RED: Wrote test for divide(8, 2) expecting 4
     * - RED: Wrote test for divide by zero expecting exception
     * - GREEN: Implemented division with zero check
     * - REFACTOR: Extracted validation logic for clarity
     */
    public int divide(int a, int b) {
        validateDivisor(b);
        return a / b;
    }

    /**
     * TDD Cycle 5: Power
     * - RED: Wrote test for power(2, 3) expecting 8
     * - GREEN: Implemented using iterative approach
     * - REFACTOR: Improved with proper handling of edge cases
     */
    public int power(int base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Negative exponents not supported");
        }

        if (exponent == 0) {
            return 1;
        }

        int result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }

        return result;
    }

    // REFACTOR: Extracted validation method (TDD Cycle 4)
    private void validateDivisor(int divisor) {
        if (divisor == 0) {
            throw new ArithmeticException("Division by zero is not allowed");
        }
    }
}
