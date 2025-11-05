package com.fose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("UserService Integration Tests")
class UserServiceIntTest {

    private UserRepository userRepository;
    private EmailService emailService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Using real implementations instead of mocks
        userRepository = new InMemoryUserRepository();
        emailService = new ConsoleEmailService();
        userService = new UserService(userRepository, emailService);
    }

    @Test
    @DisplayName("Should throw exception when registering duplicate username")
    void shouldThrowExceptionWhenRegisteringDuplicateUsername() {
        String username = "testuser";
        String email = "test@example.com";

        // Register user first time
        userService.registerUser(username, email);

        // Try to register same username again
        assertThrows(IllegalArgumentException.class,
            () -> userService.registerUser(username, "different@example.com"));
    }

    @Test
    @DisplayName("Should register valid user and increment count")
    void shouldRegisterValidUserAndIncrementCount() {
        assertEquals(0, userRepository.count());

        userService.registerUser("newuser", "newuser@example.com");

        assertEquals(1, userRepository.count());
    }

    @Test
    @DisplayName("Should register multiple unique users")
    void shouldRegisterMultipleUniqueUsers() {
        userService.registerUser("user1", "user1@example.com");
        userService.registerUser("user2", "user2@example.com");
        userService.registerUser("user3", "user3@example.com");

        assertEquals(3, userRepository.count());
    }

    @Test
    @DisplayName("Should throw exception for invalid email format")
    void shouldThrowExceptionForInvalidEmailFormat() {
        assertThrows(IllegalArgumentException.class,
            () -> userService.registerUser("testuser", "invalidemail"));
    }

    @Test
    @DisplayName("Should verify user exists after registration")
    void shouldVerifyUserExistsAfterRegistration() {
        String username = "checkuser";

        assertFalse(userRepository.existsByUsername(username));

        userService.registerUser(username, "check@example.com");

        assertTrue(userRepository.existsByUsername(username));
    }
}