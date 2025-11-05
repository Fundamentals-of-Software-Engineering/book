package com.fose.jdbc;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Demonstrates direct database access using JDBC.
 * This is the example from Chapter 8 showing low-level database operations.
 */
public class DatabaseManager {
    private final DataSource dataSource;

    public DatabaseManager(String jdbcUrl) {
        // HikariCP connection pool configuration (from the chapter)
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdbcUrl);
        config.setMaximumPoolSize(10);
        this.dataSource = new HikariDataSource(config);
    }

    /**
     * Initialize database schema
     */
    public void initializeSchema() {
        // SQL from the chapter - creating tables with relationships
        String createUserTable = """
            CREATE TABLE IF NOT EXISTS users (
                id IDENTITY PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        String createPostTable = """
            CREATE TABLE IF NOT EXISTS posts (
                id IDENTITY PRIMARY KEY,
                user_id BIGINT REFERENCES users(id),
                title VARCHAR(200) NOT NULL,
                content TEXT NOT NULL,
                published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;

        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createUserTable);
            stmt.executeUpdate(createPostTable);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize schema", e);
        }
    }

    /**
     * Insert a user using prepared statements (from the chapter).
     * Prepared statements are safer and more efficient than string concatenation.
     */
    public Long createUser(String username, String email) {
        String sql = "INSERT INTO users (username, email) VALUES (?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.executeUpdate();

            // Get the generated ID
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new SQLException("Failed to get generated ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    /**
     * Find all active users (example from the chapter showing result set mapping)
     */
    public List<User> findAllUsers() {
        String query = "SELECT id, username, email, created_at FROM users ORDER BY created_at DESC";
        List<User> users = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                    rs.getLong("id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getTimestamp("created_at").toLocalDateTime()
                );
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find users", e);
        }

        return users;
    }

    /**
     * Create a post for a user
     */
    public Long createPost(Long userId, String title, String content) {
        String sql = "INSERT INTO posts (user_id, title, content) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setLong(1, userId);
            pstmt.setString(2, title);
            pstmt.setString(3, content);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new SQLException("Failed to get generated ID");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create post", e);
        }
    }

    /**
     * Find posts by user ID
     */
    public List<Post> findPostsByUserId(Long userId) {
        String query = "SELECT id, user_id, title, content, published_at FROM posts " +
                      "WHERE user_id = ? ORDER BY published_at DESC";
        List<Post> posts = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Post post = new Post(
                        rs.getLong("id"),
                        rs.getLong("user_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getTimestamp("published_at").toLocalDateTime()
                    );
                    posts.add(post);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find posts", e);
        }

        return posts;
    }

    /**
     * Demonstrates a transaction - both operations succeed or both fail.
     * This is the example from the chapter showing proper transaction handling.
     */
    public void createUserAndPost(String username, String email, String postTitle, String postContent) {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // First operation: create user
                Long userId;
                String insertUser = "INSERT INTO users (username, email) VALUES (?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, email);
                    pstmt.executeUpdate();

                    try (ResultSet rs = pstmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            userId = rs.getLong(1);
                        } else {
                            throw new SQLException("Failed to get user ID");
                        }
                    }
                }

                // Second operation: create post
                String insertPost = "INSERT INTO posts (user_id, title, content) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertPost)) {
                    pstmt.setLong(1, userId);
                    pstmt.setString(2, postTitle);
                    pstmt.setString(3, postContent);
                    pstmt.executeUpdate();
                }

                // Both succeeded - commit the transaction
                conn.commit();
            } catch (SQLException e) {
                // Something failed - rollback everything
                conn.rollback();
                throw new RuntimeException("Transaction failed", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database connection failed", e);
        }
    }

    /**
     * Close the data source
     */
    public void close() {
        if (dataSource instanceof HikariDataSource) {
            ((HikariDataSource) dataSource).close();
        }
    }
}
