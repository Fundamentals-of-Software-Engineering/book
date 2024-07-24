package com.fose;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUserRegistration() {
        String username = "newuser";
        String email = "newuser@example.com";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        userService.registerUser(username, email);

        verify(userRepository).save(any(User.class));
        verify(emailService).sendWelcomeEmail(email);
    }

}