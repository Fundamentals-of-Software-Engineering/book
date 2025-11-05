package com.fose.jdbc;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a blog post in the database.
 * Demonstrates a foreign key relationship to User (user_id).
 */
public class Post {
    private Long id;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime publishedAt;

    public Post() {
    }

    public Post(Long id, Long userId, String title, String content, LocalDateTime publishedAt) {
        this.id = id;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.publishedAt = publishedAt;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Post{" +
               "id=" + id +
               ", userId=" + userId +
               ", title='" + title + '\'' +
               ", content='" + content + '\'' +
               ", publishedAt=" + publishedAt +
               '}';
    }
}
