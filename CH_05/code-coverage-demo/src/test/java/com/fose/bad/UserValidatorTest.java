package com.fose.bad;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * BAD EXAMPLE: Tests that achieve high coverage but provide no real value.
 * This demonstrates "gaming" the coverage metrics.
 *
 * Problems with these tests:
 * 1. No assertions - just calling methods
 * 2. Doesn't verify correct behavior
 * 3. Won't catch bugs if implementation changes
 * 4. Coverage report shows 100% but tests are worthless
 */
@DisplayName("UserValidator Tests (Bad Example)")
class UserValidatorTest {

    private final UserValidator validator = new UserValidator();

    @Test
    @DisplayName("Test email validation")
    void testEmailValidation() {
        // BAD: Calls the method but doesn't assert anything!
        validator.validateEmail("test@example.com");
        validator.validateEmail(null);
        validator.validateEmail("");
        validator.validateEmail("invalid");

        // Code coverage: 100% ✓
        // Actual value: 0% - no verification!
    }

    @Test
    @DisplayName("Test age validation")
    void testAgeValidation() {
        // BAD: No assertions, just calling the method
        validator.validateAge(18);
        validator.validateAge(25);
        validator.validateAge(120);
        validator.validateAge(10);
        validator.validateAge(150);

        // Coverage shows all lines executed
        // But we're not testing if it returns the RIGHT values!
    }

    @Test
    @DisplayName("Test username validation")
    void testUsernameValidation() {
        // BAD: Executes code but doesn't verify behavior
        validator.validateUsername("user123");
        validator.validateUsername(null);
        validator.validateUsername("ab");
        validator.validateUsername("user@name");

        // This test would pass even if the method always returned false!
    }

    @Test
    @DisplayName("Test welcome message")
    void testWelcomeMessage() {
        // BAD: Calls method but doesn't check the output
        String message = validator.generateWelcomeMessage("John");

        // We got a message, but is it correct? Who knows!
        // This test would pass if the method returned "Goodbye" instead
    }

    @Test
    @DisplayName("Test account age calculation")
    void testAccountAgeCalculation() {
        // BAD: Executes the code but doesn't verify the math
        validator.calculateAccountAge(2020);
        validator.calculateAccountAge(2010);
        validator.calculateAccountAge(2025);

        // Coverage: 100% ✓
        // Protection against bugs: 0%
    }

    /*
     * COVERAGE REPORT WILL SHOW:
     * - Line Coverage: ~100%
     * - Branch Coverage: ~100%
     * - Method Coverage: 100%
     *
     * ACTUAL VALUE: Nearly zero!
     *
     * These tests execute every line of code but verify nothing.
     * If a bug is introduced, these tests will still pass.
     * This is what the book warns against:
     * "A test that calls a method but doesn't assert anything useful
     *  still counts toward coverage yet provides no real protection
     *  against bugs."
     */
}
