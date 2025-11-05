package com.fose.scout;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * THE SCOUT RULE IN ACTION
 *
 * These tests prove that the "after" version (with Scout Rule improvements)
 * has EXACTLY the same behavior as the "before" version.
 *
 * The Scout Rule says: "Always leave the code better than you found it."
 *
 * Small improvements we made:
 * - Better naming
 * - Added documentation
 * - Used modern Java features
 * - Removed dead code
 * - Made constants clearer
 *
 * All without changing the actual behavior!
 */
@DisplayName("CustomerProcessor - Scout Rule Example")
class CustomerProcessorTest {

    /**
     * Tests for the BEFORE version (messy but functional).
     */
    @Nested
    @DisplayName("Before Scout Rule (Original Code)")
    class BeforeScoutRuleTests {

        private final com.fose.scout.before.CustomerProcessor processor =
                new com.fose.scout.before.CustomerProcessor();

        @Test
        @DisplayName("Should mark active customer with $1500 purchases as premium")
        void shouldMarkHighValueCustomerAsPremium() {
            // Given: Active customer with purchases over threshold
            var customer = new com.fose.scout.before.Customer(
                    "Alice",
                    "alice@example.com",
                    1500.0,
                    true
            );
            List customers = Arrays.asList(customer);

            // When: Processing customers
            processor.prcs(customers);

            // Then: Customer should be marked as premium
            assertThat(customer.isPremium()).isTrue();
        }

        @Test
        @DisplayName("Should not mark customer with $900 purchases as premium")
        void shouldNotMarkLowValueCustomerAsPremium() {
            // Given: Active customer with purchases under threshold
            var customer = new com.fose.scout.before.Customer(
                    "Bob",
                    "bob@example.com",
                    900.0,
                    true
            );
            List customers = Arrays.asList(customer);

            // When: Processing customers
            processor.prcs(customers);

            // Then: Customer should NOT be marked as premium
            assertThat(customer.isPremium()).isFalse();
        }

        @Test
        @DisplayName("Should not process inactive customers")
        void shouldNotProcessInactiveCustomers() {
            // Given: Inactive customer with high purchases
            var customer = new com.fose.scout.before.Customer(
                    "Charlie",
                    "charlie@example.com",
                    2000.0,
                    false // Inactive
            );
            List customers = Arrays.asList(customer);

            // When: Processing customers
            processor.prcs(customers);

            // Then: Customer should NOT be marked as premium (not processed)
            assertThat(customer.isPremium()).isFalse();
        }

        @Test
        @DisplayName("Active customer with $600 purchases should qualify for discount")
        void shouldQualifyForDiscount() {
            // Given: Active customer with purchases over discount threshold
            var customer = new com.fose.scout.before.Customer(
                    "Diana",
                    "diana@example.com",
                    600.0,
                    true
            );

            // When: Checking discount qualification
            boolean qualifies = processor.chk(customer);

            // Then: Should qualify
            assertThat(qualifies).isTrue();
        }

        @Test
        @DisplayName("Customer with $400 purchases should not qualify for discount")
        void shouldNotQualifyForDiscount() {
            // Given: Customer with purchases under discount threshold
            var customer = new com.fose.scout.before.Customer(
                    "Eve",
                    "eve@example.com",
                    400.0,
                    true
            );

            // When: Checking discount qualification
            boolean qualifies = processor.chk(customer);

            // Then: Should not qualify
            assertThat(qualifies).isFalse();
        }

        @Test
        @DisplayName("Inactive customer should not qualify for discount")
        void inactiveCustomerShouldNotQualifyForDiscount() {
            // Given: Inactive customer with high purchases
            var customer = new com.fose.scout.before.Customer(
                    "Frank",
                    "frank@example.com",
                    1000.0,
                    false
            );

            // When: Checking discount qualification
            boolean qualifies = processor.chk(customer);

            // Then: Should not qualify (inactive)
            assertThat(qualifies).isFalse();
        }
    }

    /**
     * Tests for the AFTER version (cleaned up with Scout Rule).
     * These tests are IDENTICAL to the "before" tests.
     * If they all pass, we successfully improved the code without breaking anything!
     */
    @Nested
    @DisplayName("After Scout Rule (Improved Code)")
    class AfterScoutRuleTests {

        private final com.fose.scout.after.CustomerProcessor processor =
                new com.fose.scout.after.CustomerProcessor();

        @Test
        @DisplayName("Should mark active customer with $1500 purchases as premium")
        void shouldMarkHighValueCustomerAsPremium() {
            // Same test as before - behavior is identical
            var customer = new com.fose.scout.after.Customer(
                    "Alice",
                    "alice@example.com",
                    1500.0,
                    true
            );
            List<com.fose.scout.after.Customer> customers = Arrays.asList(customer);

            processor.processCustomers(customers);

            assertThat(customer.isPremium()).isTrue();
        }

        @Test
        @DisplayName("Should not mark customer with $900 purchases as premium")
        void shouldNotMarkLowValueCustomerAsPremium() {
            var customer = new com.fose.scout.after.Customer(
                    "Bob",
                    "bob@example.com",
                    900.0,
                    true
            );
            List<com.fose.scout.after.Customer> customers = Arrays.asList(customer);

            processor.processCustomers(customers);

            assertThat(customer.isPremium()).isFalse();
        }

        @Test
        @DisplayName("Should not process inactive customers")
        void shouldNotProcessInactiveCustomers() {
            var customer = new com.fose.scout.after.Customer(
                    "Charlie",
                    "charlie@example.com",
                    2000.0,
                    false
            );
            List<com.fose.scout.after.Customer> customers = Arrays.asList(customer);

            processor.processCustomers(customers);

            assertThat(customer.isPremium()).isFalse();
        }

        @Test
        @DisplayName("Active customer with $600 purchases should qualify for discount")
        void shouldQualifyForDiscount() {
            var customer = new com.fose.scout.after.Customer(
                    "Diana",
                    "diana@example.com",
                    600.0,
                    true
            );

            boolean qualifies = processor.qualifiesForDiscount(customer);

            assertThat(qualifies).isTrue();
        }

        @Test
        @DisplayName("Customer with $400 purchases should not qualify for discount")
        void shouldNotQualifyForDiscount() {
            var customer = new com.fose.scout.after.Customer(
                    "Eve",
                    "eve@example.com",
                    400.0,
                    true
            );

            boolean qualifies = processor.qualifiesForDiscount(customer);

            assertThat(qualifies).isFalse();
        }

        @Test
        @DisplayName("Inactive customer should not qualify for discount")
        void inactiveCustomerShouldNotQualifyForDiscount() {
            var customer = new com.fose.scout.after.Customer(
                    "Frank",
                    "frank@example.com",
                    1000.0,
                    false
            );

            boolean qualifies = processor.qualifiesForDiscount(customer);

            assertThat(qualifies).isFalse();
        }
    }

    /**
     * KEY LESSONS FROM THE SCOUT RULE:
     *
     * 1. Small improvements add up
     *    - Better names, documentation, modern syntax all help
     *
     * 2. Don't need permission to improve
     *    - These changes don't require approval for major refactoring
     *
     * 3. Tests prove behavior unchanged
     *    - We can confidently improve code when tests verify behavior
     *
     * 4. Make code easier for the next person
     *    - Including your future self!
     *
     * 5. Compound effect over time
     *    - If everyone follows the Scout Rule, codebases improve continuously
     *
     * Compare the "before" and "after" code side-by-side.
     * Notice how the "after" version is SO much easier to understand,
     * yet does exactly the same thing!
     */
}
