package com.fose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * PaymentProcessorTests demonstrates how well-named tests serve as documentation.
 * By reading the test names, you can understand:
 * - What features are supported (credit cards)
 * - What features are NOT supported (Orange Pay)
 * - Business rules and validation logic
 * - Expected behavior in various scenarios
 */
@DisplayName("Payment Processor Tests")
class PaymentProcessorTests {

    private PaymentProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new PaymentProcessor();
    }

    // Test names from the book

    @Test
    @DisplayName("Should validate valid credit card number")
    void shouldValidateValidCreditCardNumber() {
        // Valid Visa card number (test number)
        assertTrue(processor.validateCreditCardNumber("4532015112830366"));

        // Valid MasterCard (test number)
        assertTrue(processor.validateCreditCardNumber("5425233430109903"));

        // Valid with spaces
        assertTrue(processor.validateCreditCardNumber("4532 0151 1283 0366"));

        // Valid with dashes
        assertTrue(processor.validateCreditCardNumber("4532-0151-1283-0366"));
    }

    @Test
    @DisplayName("Should process credit card transaction")
    void shouldProcessCreditCardTransaction() {
        PaymentResult result = processor.processCreditCardTransaction("4532015112830366", 99.99);

        assertTrue(result.success());
        assertTrue(result.message().contains("processed successfully"));
        assertTrue(result.message().contains("$99.99"));
    }

    @Test
    @DisplayName("Should process Orange Pay")
    void shouldProcessOrangePay() {
        // This test documents that Orange Pay processing exists as a method
        // but reveals through an exception that it's not actually supported
        UnsupportedPaymentMethodException exception = assertThrows(
            UnsupportedPaymentMethodException.class,
            () -> processor.processOrangePay("ORANGE123", 50.00)
        );

        assertTrue(exception.getMessage().contains("Orange Pay"));
        assertTrue(exception.getMessage().contains("not currently supported"));
    }

    @Test
    @DisplayName("Should fail when credit card type is Orange Pay")
    void shouldFailWhenCreditCardTypeIsOrangePay() {
        // This test reveals the business rule: Orange Pay is not supported
        assertFalse(processor.isCreditCardTypeSupported("OrangePay"));
        assertFalse(processor.isCreditCardTypeSupported("ORANGEPAY"));
    }

    // Additional tests that further document the system

    @Test
    @DisplayName("Should reject invalid credit card numbers")
    void shouldRejectInvalidCreditCardNumbers() {
        assertFalse(processor.validateCreditCardNumber(null));
        assertFalse(processor.validateCreditCardNumber(""));
        assertFalse(processor.validateCreditCardNumber("   "));
        assertFalse(processor.validateCreditCardNumber("1234567890123")); // Invalid Luhn
        assertFalse(processor.validateCreditCardNumber("abcd-efgh-ijkl")); // Non-numeric
        assertFalse(processor.validateCreditCardNumber("123")); // Too short
    }

    @Test
    @DisplayName("Should support major credit card types")
    void shouldSupportMajorCreditCardTypes() {
        assertTrue(processor.isCreditCardTypeSupported("VISA"));
        assertTrue(processor.isCreditCardTypeSupported("MASTERCARD"));
        assertTrue(processor.isCreditCardTypeSupported("AMEX"));
        assertTrue(processor.isCreditCardTypeSupported("DISCOVER"));
    }

    @Test
    @DisplayName("Should reject unsupported payment types")
    void shouldRejectUnsupportedPaymentTypes() {
        assertFalse(processor.isCreditCardTypeSupported("PAYPAL"));
        assertFalse(processor.isCreditCardTypeSupported("BITCOIN"));
        assertFalse(processor.isCreditCardTypeSupported("APPLEPAY"));
    }

    @Test
    @DisplayName("Should fail transaction with invalid card number")
    void shouldFailTransactionWithInvalidCardNumber() {
        PaymentResult result = processor.processCreditCardTransaction("invalid", 100.00);

        assertFalse(result.success());
        assertTrue(result.message().contains("Invalid credit card number"));
    }

    @Test
    @DisplayName("Should fail transaction with zero amount")
    void shouldFailTransactionWithZeroAmount() {
        PaymentResult result = processor.processCreditCardTransaction("4532015112830366", 0.00);

        assertFalse(result.success());
        assertTrue(result.message().contains("greater than zero"));
    }

    @Test
    @DisplayName("Should fail transaction with negative amount")
    void shouldFailTransactionWithNegativeAmount() {
        PaymentResult result = processor.processCreditCardTransaction("4532015112830366", -50.00);

        assertFalse(result.success());
        assertTrue(result.message().contains("greater than zero"));
    }

    @Test
    @DisplayName("Should handle large transaction amounts")
    void shouldHandleLargeTransactionAmounts() {
        PaymentResult result = processor.processCreditCardTransaction("4532015112830366", 9999.99);

        assertTrue(result.success());
        assertTrue(result.message().contains("$9999.99"));
    }

    /*
     * HOW THESE TESTS SERVE AS DOCUMENTATION:
     *
     * 1. Feature Discovery: Reading test names tells you what features exist
     *    - Credit card validation
     *    - Transaction processing
     *    - Payment type support checking
     *
     * 2. Business Rules: Tests reveal important business decisions
     *    - Orange Pay is NOT supported (shouldFailWhenCreditCardTypeIsOrangePay)
     *    - Only certain card types are accepted
     *    - Amounts must be positive
     *
     * 3. Edge Cases: Tests document expected behavior in edge cases
     *    - Empty/null card numbers
     *    - Zero or negative amounts
     *    - Invalid card formats
     *
     * 4. Navigation: Modern IDEs let you jump from test to implementation
     *    - Click on processOrangePay() → see it throws exception
     *    - Click on isCreditCardTypeSupported() → see the switch statement
     *
     * This is exactly what the book describes:
     * "These tests provide insight into the module's features and point to
     *  relevant code sections... You might discover a test named
     *  shouldFailWhenCreditCardTypeIsOrangePay, revealing that Orange Pay
     *  isn't supported."
     */
}
