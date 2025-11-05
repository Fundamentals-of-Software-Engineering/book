package com.fose;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Operations Test Suite")
class OperationsTest {

    private Operations operations;

    @BeforeAll
    static void setupAll() {
        System.out.println("Starting Operations test suite");
    }

    @BeforeEach
    void setup() {
        operations = new Operations();
    }

    @AfterEach
    void tearDown() {
        operations = null;
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println("Completed Operations test suite");
    }

    @Test
    @DisplayName("Should add two positive numbers correctly")
    void shouldAddTwoPositiveNumbers() {
        double result = operations.add(2, 3);
        assertEquals(5, result, "2 + 3 should equal 5");
    }

    @Test
    @DisplayName("Should add positive and negative numbers correctly")
    void shouldAddPositiveAndNegativeNumbers() {
        assertEquals(0, operations.add(-1, 1));
        assertEquals(5, operations.add(10, -5));
    }

    @Test
    @DisplayName("Should add two negative numbers correctly")
    void shouldAddTwoNegativeNumbers() {
        assertEquals(-5, operations.add(-2, -3));
    }

    @Test
    @DisplayName("Should handle adding zero")
    void shouldHandleAddingZero() {
        assertEquals(5, operations.add(5, 0));
        assertEquals(5, operations.add(0, 5));
    }

    @Test
    @DisplayName("Should subtract numbers correctly")
    void shouldSubtractNumbers() {
        assertAll("subtraction operations",
            () -> assertEquals(1, operations.subtract(3, 2)),
            () -> assertEquals(-2, operations.subtract(-1, 1)),
            () -> assertEquals(5, operations.subtract(2, -3))
        );
    }

    @Test
    @DisplayName("Should multiply numbers correctly")
    void shouldMultiplyNumbers() {
        assertEquals(6, operations.multiply(2, 3));
        assertEquals(-6, operations.multiply(-2, 3));
        assertEquals(6, operations.multiply(-2, -3));
    }

    @Test
    @DisplayName("Should handle multiplication by zero")
    void shouldHandleMultiplicationByZero() {
        assertEquals(0, operations.multiply(5, 0));
        assertEquals(0, operations.multiply(0, 5));
    }

    @Test
    @DisplayName("Should divide numbers correctly")
    void shouldDivideNumbers() {
        assertEquals(2, operations.divide(8, 4));
        assertEquals(2.5, operations.divide(5, 2));
        assertEquals(-2, operations.divide(6, -3));
    }

    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> operations.divide(5, 0),
            "Should throw ArithmeticException when dividing by zero"
        );

        assertTrue(exception.getMessage().contains("Division by zero"));
    }

    @Test
    @DisplayName("Should handle decimal division correctly")
    void shouldHandleDecimalDivision() {
        double result = operations.divide(7, 2);
        assertEquals(3.5, result, 0.001);
    }

    @Test
    @DisplayName("Should verify operations object is not null")
    void shouldVerifyOperationsObjectIsNotNull() {
        assertNotNull(operations);
    }

    @Test
    @DisplayName("Should verify multiple assertions with assertAll")
    void shouldVerifyMultipleAssertionsWithAssertAll() {
        assertAll("All basic operations",
            () -> assertEquals(5, operations.add(2, 3)),
            () -> assertEquals(1, operations.subtract(3, 2)),
            () -> assertEquals(6, operations.multiply(2, 3)),
            () -> assertEquals(2, operations.divide(8, 4))
        );
    }
}