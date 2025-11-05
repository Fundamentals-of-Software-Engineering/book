package com.fose;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService Unit Tests (with Mocks)")
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Should register new user when username is unique")
    void shouldRegisterNewUserWhenUsernameIsUnique() {
        String username = "newuser";
        String email = "newuser@example.com";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        userService.registerUser(username, email);

        verify(userRepository).save(any(User.class));
        verify(emailService).sendWelcomeEmail(email);
    }

    @Test
    @DisplayName("Should throw exception when username already exists")
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        String username = "existinguser";
        String email = "existing@example.com";

        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
            userService.registerUser(username, email)
        );

        verify(userRepository, never()).save(any(User.class));
        verify(emailService, never()).sendWelcomeEmail(any());
    }

    @Test
    @DisplayName("Should save user with correct username and email")
    void shouldSaveUserWithCorrectUsernameAndEmail() {
        String username = "testuser";
        String email = "test@example.com";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        userService.registerUser(username, email);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());

        User savedUser = userCaptor.getValue();
        assertEquals(username, savedUser.username());
        assertEquals(email, savedUser.email());
    }

    @Test
    @DisplayName("Should call email service exactly once with correct email")
    void shouldCallEmailServiceExactlyOnceWithCorrectEmail() {
        String username = "newuser";
        String email = "newuser@example.com";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        userService.registerUser(username, email);

        verify(emailService, times(1)).sendWelcomeEmail(email);
    }

    @Test
    @DisplayName("Should verify repository is checked before saving")
    void shouldVerifyRepositoryIsCheckedBeforeSaving() {
        String username = "checkuser";
        String email = "check@example.com";

        when(userRepository.existsByUsername(username)).thenReturn(false);

        userService.registerUser(username, email);

        verify(userRepository).existsByUsername(username);
        verify(userRepository).save(any(User.class));
    }
}