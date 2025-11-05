package com.fose.repository;

import java.util.List;

/**
 * AFTER: Clean business logic using the repository pattern
 *
 * Benefits:
 * - Business logic is separated from data access details
 * - Easy to test with mock repositories
 * - No SQL or database knowledge required in this class
 * - Changes to database schema don't affect this class
 */
public class UserServiceWithRepository {
    private final UserRepository userRepository;

    public UserServiceWithRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Clean business logic - no SQL or database details
     */
    public List<User> getActiveUsers() {
        // Simple call to repository - no database code here
        return userRepository.findActiveUsers();
    }

    /**
     * Business logic for creating users - focused on validation and rules
     */
    public void registerUser(String username, String email) {
        // Validate business rules
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        // Create and save user through repository
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setActive(true);

        userRepository.save(user);
    }
}
