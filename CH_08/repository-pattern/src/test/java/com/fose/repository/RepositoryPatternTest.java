package com.fose.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates the difference between direct SQL access and repository pattern
 */
class RepositoryPatternTest {
    private DataSource dataSource;
    private static int testCounter = 0;

    @BeforeEach
    void setUp() throws Exception {
        testCounter++;
        // Create in-memory H2 database with close delay
        org.h2.jdbcx.JdbcDataSource ds = new org.h2.jdbcx.JdbcDataSource();
        ds.setURL("jdbc:h2:mem:testdb" + testCounter + ";DB_CLOSE_DELAY=-1");
        dataSource = ds;

        // Initialize schema
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    id IDENTITY PRIMARY KEY,
                    username VARCHAR(50) NOT NULL,
                    email VARCHAR(100) NOT NULL,
                    active BOOLEAN DEFAULT TRUE,
                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
                )
            """);
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        // Clean up
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP ALL OBJECTS");
        }
    }

    @Test
    void directSQL_MixesBusinessLogicWithDataAccess() {
        // BEFORE: Direct SQL approach
        UserServiceDirectSQL service = new UserServiceDirectSQL(dataSource);

        // Create users
        service.createUser("alice", "alice@example.com");
        service.createUser("bob", "bob@example.com");

        // Get active users
        List<User> users = service.getActiveUsers();

        assertEquals(2, users.size());

        // Problems with this approach:
        // 1. Hard to test without a database
        // 2. SQL is scattered throughout the code
        // 3. Changes to schema affect business logic
        System.out.println("✗ Direct SQL: Business logic tightly coupled to database");
    }

    @Test
    void repositoryPattern_SeparatesConcerns() {
        // AFTER: Repository pattern
        UserRepository repository = new UserRepository(dataSource);
        UserServiceWithRepository service = new UserServiceWithRepository(repository);

        // Register users (notice the business method name)
        service.registerUser("alice", "alice@example.com");
        service.registerUser("bob", "bob@example.com");

        // Get active users
        List<User> users = service.getActiveUsers();

        assertEquals(2, users.size());

        // Benefits of this approach:
        // 1. Business logic is clean and testable
        // 2. Repository can be easily mocked
        // 3. Database changes don't affect service layer
        System.out.println("✓ Repository Pattern: Clean separation of concerns");
    }

    @Test
    void repositoryPattern_EnablesEasyTesting() {
        // The repository pattern makes it easy to create mock implementations
        UserRepository repository = new UserRepository(dataSource);
        UserServiceWithRepository service = new UserServiceWithRepository(repository);

        // Business logic validation works independently
        assertThrows(IllegalArgumentException.class, () -> {
            service.registerUser("", "test@example.com");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            service.registerUser("test", "invalid-email");
        });

        System.out.println("✓ Repository Pattern: Business validation is independent");
    }

    @Test
    void repositoryPattern_CentralizesDataAccess() {
        // All database operations are in the repository
        UserRepository repository = new UserRepository(dataSource);

        // Create users directly through repository
        User user1 = new User();
        user1.setUsername("alice");
        user1.setEmail("alice@example.com");
        user1.setActive(true);
        repository.save(user1);

        User user2 = new User();
        user2.setUsername("bob");
        user2.setEmail("bob@example.com");
        user2.setActive(true);
        repository.save(user2);

        // Query through repository
        List<User> activeUsers = repository.findActiveUsers();

        assertEquals(2, activeUsers.size());
        System.out.println("✓ Repository: All data access in one place");
    }
}
