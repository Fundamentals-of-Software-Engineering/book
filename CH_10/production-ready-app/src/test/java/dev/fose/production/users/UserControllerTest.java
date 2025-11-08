package dev.fose.production.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for UserController demonstrating API testing.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/users";
    }

    @Test
    void shouldCreateUserSuccessfully() {
        User user = new User("testuser", "test@example.com", "securePassword123!");

        ResponseEntity<User> response = restTemplate.postForEntity(
            getBaseUrl(),
            user,
            User.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getUsername()).isEqualTo("testuser");
        assertThat(response.getBody().getId()).isNotNull();
    }

    @Test
    void shouldReturnValidationErrorForInvalidUser() {
        User user = new User("ab", "invalid-email", "short");

        ResponseEntity<String> response = restTemplate.postForEntity(
            getBaseUrl(),
            user,
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Validation failed");
    }

    @Test
    void shouldGetAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(
            getBaseUrl(),
            User[].class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldReturn404ForNonexistentUser() {
        ResponseEntity<String> response = restTemplate.getForEntity(
            getBaseUrl() + "/99999",
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).contains("could not be found");
    }
}
