package com.fose.refactoring;

import com.fose.refactoring.model.Order;
import com.fose.refactoring.model.OrderItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * TEST-DRIVEN REFACTORING EXAMPLE
 *
 * This test class demonstrates a critical principle:
 * WRITE TESTS BEFORE REFACTORING!
 *
 * Process:
 * 1. Write tests that document the CURRENT behavior (even if it's messy)
 * 2. Verify tests pass with the original "before" implementation
 * 3. Refactor the code
 * 4. Verify tests STILL pass with the "after" implementation
 * 5. If tests pass, refactoring preserved behavior ✅
 * 6. If tests fail, you broke something ❌
 *
 * These tests run against BOTH the before and after implementations
 * to prove they have identical behavior.
 */
@DisplayName("OrderService - Before and After Refactoring")
class OrderServiceTest {

    /**
     * Tests for the BEFORE refactoring version.
     * These tests document the existing behavior.
     */
    @Nested
    @DisplayName("Before Refactoring (Complex Implementation)")
    class BeforeRefactoringTests {

        private final com.fose.refactoring.before.OrderService orderService =
                new com.fose.refactoring.before.OrderService();

        @Test
        @DisplayName("Should calculate total for simple order with no discounts")
        void shouldCalculateTotalForSimpleOrder() {
            // Given: An order with two items, total = $50
            Order order = new Order(Arrays.asList(
                    new OrderItem("Product A", 20.0, 1), // $20
                    new OrderItem("Product B", 30.0, 1)  // $30
            ));
            // Subtotal: $50
            // Shipping: $10 (under $100)
            // Tax: ($50 + $10) * 0.08 = $4.80
            // Total: $60 + $4.80 = $64.80

            // When: Calculating the total
            double total = orderService.calculateTotal(order);

            // Then: Total should be $64.80
            assertThat(total).isEqualTo(64.80);
        }

        @Test
        @DisplayName("Should apply premium customer discount (10%)")
        void shouldApplyPremiumCustomerDiscount() {
            // Given: Premium customer order, subtotal = $100
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 100.0, 1)),
                    true  // Premium customer
            );
            // Subtotal: $100
            // Premium discount: $100 * 0.9 = $90
            // Shipping: $10 (discounted total of $90 is under $100 threshold)
            // Subtotal with shipping: $90 + $10 = $100
            // Tax: $100 * 0.08 = $8.00
            // Total: $100 + $8.00 = $108.00

            // When
            double total = orderService.calculateTotal(order);

            // Then
            assertThat(total).isEqualTo(108.00);
        }

        @Test
        @DisplayName("Should apply promotional discount code")
        void shouldApplyPromotionalDiscount() {
            // Given: Order with 20% discount code
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 100.0, 1)),
                    false,      // Not premium customer
                    "SAVE20",   // Discount code
                    0.20        // 20% discount rate
            );
            // Subtotal: $100
            // Discount: $100 * 0.8 = $80
            // Shipping: $10 (under $100)
            // Tax: ($80 + $10) * 0.08 = $7.20
            // Total: $90 + $7.20 = $97.20

            // When
            double total = orderService.calculateTotal(order);

            // Then
            assertThat(total).isEqualTo(97.20);
        }

        @Test
        @DisplayName("Should get free shipping for orders over $100")
        void shouldGetFreeShippingForLargeOrders() {
            // Given: Order totaling $150
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 150.0, 1))
            );
            // Subtotal: $150
            // Shipping: $0 (free over $100)
            // Subtotal with shipping: $150
            // Tax: $150 * 0.08 = $12.00 (standard rate, under $500 threshold)
            // Total: $150 + $12.00 = $162.00

            // When
            double total = orderService.calculateTotal(order);

            // Then
            assertThat(total).isEqualTo(162.00);
        }

        @Test
        @DisplayName("Should apply higher tax rate for orders over $500")
        void shouldApplyHigherTaxForHighValueOrders() {
            // Given: Order totaling $600
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 600.0, 1))
            );
            // Subtotal: $600
            // Shipping: $0 (free over $100)
            // Tax: $600 * 0.15 = $90 (15% for orders >= $500)
            // Total: $600 + $90 = $690

            // When
            double total = orderService.calculateTotal(order);

            // Then
            assertThat(total).isEqualTo(690.00);
        }

        @Test
        @DisplayName("Should apply both premium and promotional discounts")
        void shouldApplyMultipleDiscounts() {
            // Given: Premium customer with discount code
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 100.0, 1)),
                    true,       // Premium customer
                    "SAVE10",   // Discount code
                    0.10        // 10% discount rate
            );
            // Subtotal: $100
            // Premium discount: $100 * 0.9 = $90
            // Promo discount: $90 * 0.9 = $81
            // Shipping: $10 (under $100)
            // Tax: ($81 + $10) * 0.08 = $7.28
            // Total: $91 + $7.28 = $98.28

            // When
            double total = orderService.calculateTotal(order);

            // Then
            assertThat(total).isEqualTo(98.28);
        }

        @Test
        @DisplayName("Premium customer should qualify for express shipping with $60 order")
        void premiumCustomerQualifiesForExpress() {
            // Given: Premium customer with order > $50
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 60.0, 1)),
                    true  // Premium customer
            );

            // When
            boolean qualifies = orderService.qualifiesForExpressShipping(order);

            // Then
            assertThat(qualifies).isTrue();
        }

        @Test
        @DisplayName("Regular customer should qualify for express shipping with $250 order")
        void regularCustomerQualifiesForExpressWithHighValue() {
            // Given: Regular customer with order > $200
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 250.0, 1))
            );

            // When
            boolean qualifies = orderService.qualifiesForExpressShipping(order);

            // Then
            assertThat(qualifies).isTrue();
        }

        @Test
        @DisplayName("Customer with EXPRESS code should qualify regardless of order value")
        void expressCodeQualifiesForExpress() {
            // Given: Order with EXPRESS discount code
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 10.0, 1)),
                    false,      // Not premium customer
                    "EXPRESS",  // Discount code
                    0.0         // No discount rate
            );

            // When
            boolean qualifies = orderService.qualifiesForExpressShipping(order);

            // Then
            assertThat(qualifies).isTrue();
        }
    }

    /**
     * Tests for the AFTER refactoring version.
     * These are IDENTICAL to the "before" tests.
     * If all tests pass, we've successfully refactored without changing behavior!
     */
    @Nested
    @DisplayName("After Refactoring (Clean Implementation)")
    class AfterRefactoringTests {

        private final com.fose.refactoring.after.OrderService orderService =
                new com.fose.refactoring.after.OrderService();

        @Test
        @DisplayName("Should calculate total for simple order with no discounts")
        void shouldCalculateTotalForSimpleOrder() {
            // Same test as before - behavior should be IDENTICAL
            Order order = new Order(Arrays.asList(
                    new OrderItem("Product A", 20.0, 1),
                    new OrderItem("Product B", 30.0, 1)
            ));

            double total = orderService.calculateTotal(order);

            assertThat(total).isEqualTo(64.80);
        }

        @Test
        @DisplayName("Should apply premium customer discount (10%)")
        void shouldApplyPremiumCustomerDiscount() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 100.0, 1)),
                    true  // Premium customer
            );

            double total = orderService.calculateTotal(order);

            assertThat(total).isEqualTo(108.00);
        }

        @Test
        @DisplayName("Should apply promotional discount code")
        void shouldApplyPromotionalDiscount() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 100.0, 1)),
                    false,      // Not premium customer
                    "SAVE20",   // Discount code
                    0.20        // 20% discount rate
            );

            double total = orderService.calculateTotal(order);

            assertThat(total).isEqualTo(97.20);
        }

        @Test
        @DisplayName("Should get free shipping for orders over $100")
        void shouldGetFreeShippingForLargeOrders() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 150.0, 1))
            );

            double total = orderService.calculateTotal(order);

            assertThat(total).isEqualTo(162.00);
        }

        @Test
        @DisplayName("Should apply higher tax rate for orders over $500")
        void shouldApplyHigherTaxForHighValueOrders() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 600.0, 1))
            );

            double total = orderService.calculateTotal(order);

            assertThat(total).isEqualTo(690.00);
        }

        @Test
        @DisplayName("Should apply both premium and promotional discounts")
        void shouldApplyMultipleDiscounts() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 100.0, 1)),
                    true,       // Premium customer
                    "SAVE10",   // Discount code
                    0.10        // 10% discount rate
            );

            double total = orderService.calculateTotal(order);

            assertThat(total).isEqualTo(98.28);
        }

        @Test
        @DisplayName("Premium customer should qualify for express shipping with $60 order")
        void premiumCustomerQualifiesForExpress() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 60.0, 1)),
                    true  // Premium customer
            );

            boolean qualifies = orderService.qualifiesForExpressShipping(order);

            assertThat(qualifies).isTrue();
        }

        @Test
        @DisplayName("Regular customer should qualify for express shipping with $250 order")
        void regularCustomerQualifiesForExpressWithHighValue() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 250.0, 1))
            );

            boolean qualifies = orderService.qualifiesForExpressShipping(order);

            assertThat(qualifies).isTrue();
        }

        @Test
        @DisplayName("Customer with EXPRESS code should qualify regardless of order value")
        void expressCodeQualifiesForExpress() {
            Order order = new Order(
                    Arrays.asList(new OrderItem("Product A", 10.0, 1)),
                    false,      // Not premium customer
                    "EXPRESS",  // Discount code
                    0.0         // No discount rate
            );

            boolean qualifies = orderService.qualifiesForExpressShipping(order);

            assertThat(qualifies).isTrue();
        }
    }

    /**
     * Key Takeaways:
     *
     * 1. Tests document behavior - They show what the code DOES, not how it does it
     * 2. Tests enable safe refactoring - Change implementation freely, tests verify behavior
     * 3. Write tests BEFORE refactoring - Don't guess at behavior, document it first
     * 4. Tests should be identical - Before and after should produce same results
     * 5. Refactoring improves code WITHOUT changing behavior
     *
     * Notice how the "after" version is much easier to understand and maintain,
     * but produces exactly the same results as the "before" version!
     */
}
