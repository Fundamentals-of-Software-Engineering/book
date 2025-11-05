package com.fose.repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AFTER: Repository pattern - encapsulates all data access logic
 *
 * Benefits of this approach:
 * - Separation of concerns: isolates data access from business logic
 * - Easier testing: can mock repositories in unit tests
 * - Code organization: all database operations in one place
 * - Single responsibility: only handles data persistence
 */
public class UserRepository {
    private final DataSource dataSource;

    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Find all active users - data access logic encapsulated
     */
    public List<User> findActiveUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT id, username, email FROM users WHERE active = true " +
                      "ORDER BY created_at DESC";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to find active users", e);
        }

        return users;
    }

    /**
     * Save a new user
     */
    public void save(User user) {
        String sql = "INSERT INTO users (username, email, active) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setBoolean(3, user.isActive());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RepositoryException("Failed to save user", e);
        }
    }

    /**
     * Custom exception for repository operations
     */
    public static class RepositoryException extends RuntimeException {
        public RepositoryException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
