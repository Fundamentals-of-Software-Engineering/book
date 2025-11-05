package com.fose.good;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GOOD EXAMPLE: Tests with meaningful assertions that verify behavior.
 * These tests may not have 100% coverage, but they provide real value.
 *
 * Advantages of these tests:
 * 1. Verify actual behavior with assertions
 * 2. Will catch bugs if implementation changes
 * 3. Serve as documentation of expected behavior
 * 4. Focus on important business logic
 */
@DisplayName("OrderProcessor Tests (Good Example)")
class OrderProcessorTest {

    private OrderProcessor processor;

    @BeforeEach
    void setUp() {
        processor = new OrderProcessor();
    }

    @Test
    @DisplayName("Should calculate total without discount for orders under $100")
    void shouldCalculateTotalWithoutDiscount() {
        BigDecimal subtotal = new BigDecimal("50.00");
        BigDecimal total = processor.calculateTotal(subtotal);

        // $50 * 1.08 (8% tax) = $54.00
        assertEquals(new BigDecimal("54.00"), total);
    }

    @Test
    @DisplayName("Should apply 10% discount for orders $100 or more")
    void shouldApplyDiscountForLargeOrders() {
        BigDecimal subtotal = new BigDecimal("100.00");
        BigDecimal total = processor.calculateTotal(subtotal);

        // $100 - $10 (10% discount) = $90
        // $90 * 1.08 (8% tax) = $97.20
        assertEquals(new BigDecimal("97.20"), total);
    }

    @Test
    @DisplayName("Should apply discount and tax correctly for large orders")
    void shouldApplyDiscountAndTaxForLargeOrders() {
        BigDecimal subtotal = new BigDecimal("200.00");
        BigDecimal total = processor.calculateTotal(subtotal);

        // $200 - $20 (10% discount) = $180
        // $180 * 1.08 (8% tax) = $194.40
        assertEquals(new BigDecimal("194.40"), total);
    }

    @Test
    @DisplayName("Should throw exception for null subtotal")
    void shouldThrowExceptionForNullSubtotal() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> processor.calculateTotal(null)
        );

        assertTrue(exception.getMessage().contains("cannot be null"));
    }

    @Test
    @DisplayName("Should throw exception for negative subtotal")
    void shouldThrowExceptionForNegativeSubtotal() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> processor.calculateTotal(new BigDecimal("-10.00"))
        );

        assertTrue(exception.getMessage().contains("negative"));
    }

    @Test
    @DisplayName("Should qualify for free shipping when total is $50 or more")
    void shouldQualifyForFreeShipping() {
        assertTrue(processor.isEligibleForFreeShipping(new BigDecimal("50.00")));
        assertTrue(processor.isEligibleForFreeShipping(new BigDecimal("100.00")));
    }

    @Test
    @DisplayName("Should not qualify for free shipping when total is under $50")
    void shouldNotQualifyForFreeShipping() {
        assertFalse(processor.isEligibleForFreeShipping(new BigDecimal("49.99")));
        assertFalse(processor.isEligibleForFreeShipping(new BigDecimal("25.00")));
    }

    @Test
    @DisplayName("Should return correct shipping method for free shipping orders")
    void shouldReturnCorrectShippingMethodForFreeShipping() {
        BigDecimal total = new BigDecimal("75.00");

        assertEquals("Standard (Free)", processor.getShippingMethod(total, false));
        assertEquals("Express (Free)", processor.getShippingMethod(total, true));
    }

    @Test
    @DisplayName("Should return correct shipping method with cost for small orders")
    void shouldReturnCorrectShippingMethodWithCost() {
        BigDecimal total = new BigDecimal("25.00");

        assertEquals("Standard ($5)", processor.getShippingMethod(total, false));
        assertEquals("Express ($15)", processor.getShippingMethod(total, true));
    }

    /*
     * COVERAGE ANALYSIS:
     *
     * These tests focus on:
     * - Critical business logic (discount calculations, tax)
     * - Edge cases (threshold values like $50, $100)
     * - Error conditions (null, negative values)
     * - Important user-facing features (shipping methods)
     *
     * We might have slightly lower coverage percentage, but:
     * - Every test verifies actual behavior
     * - Tests will fail if business logic breaks
     * - Tests document expected behavior clearly
     * - Focus is on value, not metrics
     *
     * This aligns with the book's guidance:
     * "Instead of striving for 100% coverage, which is often a
     *  vanity metric, use it as a feedback mechanism for the
     *  tests you're writing."
     *
     * COVERAGE METRICS:
     * - May show 85-90% coverage (not 100%)
     * - But provides real protection against bugs
     * - Every assertion verifies meaningful behavior
     * - Tests would catch regressions
     */
}
