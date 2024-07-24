package com.fose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntTest {

    private UserRepository userRepository;
    private EmailService emailService;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        emailService = new EmailService();
        userService = new UserService(userRepository,emailService);
    }

    @Test
    void shouldNotRegisterUserWithUsernameOfUser() {
        var user = new User("user","user@gmail.com");
        assertThrows(IllegalArgumentException.class,() -> userService.registerUser(user.username(), user.email()));
    }

    @Test
    void shouldRegisterValidUser() {
        var user = new User("dvega","danvega@gmail.com");
        userService.registerUser(user.username(),user.email());
        assertEquals(2,userRepository.count());
    }

}