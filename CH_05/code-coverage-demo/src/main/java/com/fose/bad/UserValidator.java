package com.fose.bad;

/**
 * BAD EXAMPLE: This class has high test coverage but poor quality tests.
 * Tests execute the code without meaningful assertions.
 */
public class UserValidator {

    public boolean validateEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    public boolean validateAge(int age) {
        return age >= 18 && age <= 120;
    }

    public boolean validateUsername(String username) {
        if (username == null || username.length() < 3) {
            return false;
        }
        return username.matches("[a-zA-Z0-9_]+");
    }

    public String generateWelcomeMessage(String username) {
        return "Welcome, " + username + "!";
    }

    public int calculateAccountAge(int registrationYear) {
        int currentYear = 2025;
        return currentYear - registrationYear;
    }
}
