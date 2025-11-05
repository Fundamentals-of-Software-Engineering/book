package com.fose.repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BEFORE: Direct database access mixed with business logic
 *
 * Problems with this approach:
 * - Business logic is tightly coupled to database implementation
 * - Difficult to test without a real database
 * - Database schema changes require modifications throughout the code
 * - SQL queries are scattered across the application
 */
public class UserServiceDirectSQL {
    private final DataSource dataSource;

    public UserServiceDirectSQL(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Business logic mixed with data access - hard to test and maintain
     */
    public List<User> getActiveUsers() {
        List<User> users = new ArrayList<>();

        // SQL embedded directly in business logic
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
            throw new RuntimeException("Failed to find active users", e);
        }

        return users;
    }

    /**
     * Another example - creating a user with SQL in business layer
     */
    public void createUser(String username, String email) {
        String sql = "INSERT INTO users (username, email, active) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, email);
            stmt.setBoolean(3, true);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }
}
