package com.fose;

public class EmailService {
    public void sendWelcomeEmail(String email) {
        // In a real implementation, this would send an actual email
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        System.out.println("Welcome email sent to " + email);
    }
}
