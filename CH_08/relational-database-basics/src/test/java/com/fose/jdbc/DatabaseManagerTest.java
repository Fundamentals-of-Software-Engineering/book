package com.fose.jdbc;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests demonstrating JDBC operations, prepared statements, and transactions.
 * Each test uses a fresh in-memory H2 database.
 */
class DatabaseManagerTest {
    private DatabaseManager dbManager;
    private static int testCounter = 0;

    @BeforeEach
    void setUp() {
        // Create a unique in-memory H2 database for each test
        testCounter++;
        dbManager = new DatabaseManager("jdbc:h2:mem:testdb" + testCounter);
        dbManager.initializeSchema();
    }

    @AfterEach
    void tearDown() {
        dbManager.close();
    }

    @Test
    void shouldCreateUserWithPreparedStatement() {
        // Prepared statements from the chapter - safer than string concatenation
        Long userId = dbManager.createUser("john_doe", "john@example.com");

        assertNotNull(userId);
        assertTrue(userId > 0);

        // Verify the user was created
        List<User> users = dbManager.findAllUsers();
        assertEquals(1, users.size());
        assertEquals("john_doe", users.get(0).getUsername());
        assertEquals("john@example.com", users.get(0).getEmail());
    }

    @Test
    void shouldFindAllUsers() {
        // Create multiple users
        dbManager.createUser("alice", "alice@example.com");
        dbManager.createUser("bob", "bob@example.com");
        dbManager.createUser("charlie", "charlie@example.com");

        // Find all users (from the chapter's example)
        List<User> users = dbManager.findAllUsers();

        assertEquals(3, users.size());
        // Results should be ordered by created_at DESC (most recent first)
        assertEquals("charlie", users.get(0).getUsername());
    }

    @Test
    void shouldCreatePost() {
        // Create a user first
        Long userId = dbManager.createUser("jane_doe", "jane@example.com");

        // Create a post for that user
        Long postId = dbManager.createPost(userId, "My First Post", "Hello, World!");

        assertNotNull(postId);
        assertTrue(postId > 0);

        // Verify the post was created
        List<Post> posts = dbManager.findPostsByUserId(userId);
        assertEquals(1, posts.size());
        assertEquals("My First Post", posts.get(0).getTitle());
        assertEquals("Hello, World!", posts.get(0).getContent());
    }

    @Test
    void shouldFindPostsByUser() {
        // Create a user and multiple posts
        Long userId = dbManager.createUser("author", "author@example.com");
        dbManager.createPost(userId, "First Post", "Content 1");
        dbManager.createPost(userId, "Second Post", "Content 2");
        dbManager.createPost(userId, "Third Post", "Content 3");

        // Find all posts by this user
        List<Post> posts = dbManager.findPostsByUserId(userId);

        assertEquals(3, posts.size());
        // Most recent first
        assertEquals("Third Post", posts.get(0).getTitle());
    }

    @Test
    void shouldHandleTransactionSuccessfully() {
        // Transaction example from the chapter - creates user and post atomically
        dbManager.createUserAndPost(
            "transaction_user",
            "transaction@example.com",
            "Transactional Post",
            "This post was created in a transaction"
        );

        // Verify both user and post were created
        List<User> users = dbManager.findAllUsers();
        assertEquals(1, users.size());

        Long userId = users.get(0).getId();
        List<Post> posts = dbManager.findPostsByUserId(userId);
        assertEquals(1, posts.size());
        assertEquals("Transactional Post", posts.get(0).getTitle());
    }

    @Test
    void shouldEnforceUniqueConstraints() {
        // Create a user
        dbManager.createUser("unique_user", "unique@example.com");

        // Try to create another user with the same username
        assertThrows(RuntimeException.class, () -> {
            dbManager.createUser("unique_user", "different@example.com");
        });

        // Try to create another user with the same email
        assertThrows(RuntimeException.class, () -> {
            dbManager.createUser("different_user", "unique@example.com");
        });
    }

    @Test
    void shouldEnforceForeignKeyConstraints() {
        // Try to create a post for a non-existent user
        assertThrows(RuntimeException.class, () -> {
            dbManager.createPost(999L, "Invalid Post", "This should fail");
        });
    }
}
