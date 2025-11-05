package com.fose;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("BlogPostController (Bad Design) Tests")
class BlogPostControllerBadTest {

    private BlogPostControllerBad controller;

    @BeforeEach
    void setUp() {
        controller = new BlogPostControllerBad();
    }

    @Test
    @DisplayName("Should create blog post successfully")
    void shouldCreateBlogPostSuccessfully() {
        String result = controller.createBlogPost("Test Title", "Test Content", "author@example.com");

        assertEquals("Success", result);
        assertEquals(1, controller.getPostCount());
    }

    @Test
    @DisplayName("Should return error for empty title")
    void shouldReturnErrorForEmptyTitle() {
        String result = controller.createBlogPost("", "Test Content", "author@example.com");

        assertEquals("Error: Title cannot be empty", result);
        assertEquals(0, controller.getPostCount());
    }

    @Test
    @DisplayName("Should return error for null title")
    void shouldReturnErrorForNullTitle() {
        String result = controller.createBlogPost(null, "Test Content", "author@example.com");

        assertEquals("Error: Title cannot be empty", result);
    }

    @Test
    @DisplayName("Should return error for empty content")
    void shouldReturnErrorForEmptyContent() {
        String result = controller.createBlogPost("Test Title", "", "author@example.com");

        assertEquals("Error: Content cannot be empty", result);
    }

    /*
     * LIMITATIONS OF THIS TEST:
     * 1. Cannot verify email was sent (tightly coupled to System.out)
     * 2. Cannot verify logging occurred (tightly coupled to System.out)
     * 3. Cannot inject mock dependencies for testing edge cases
     * 4. Hard to test database failures or email failures
     * 5. Tests produce console output which clutters test results
     */
}
