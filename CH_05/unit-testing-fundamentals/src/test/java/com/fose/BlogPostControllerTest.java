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
@DisplayName("BlogPostController (Good Design) Tests")
class BlogPostControllerTest {

    @Mock
    private BlogPostRepository repository;

    @Mock
    private NotificationService notificationService;

    @Mock
    private ActivityLogger logger;

    @InjectMocks
    private BlogPostController controller;

    @Test
    @DisplayName("Should create blog post and notify all services")
    void shouldCreateBlogPostAndNotifyAllServices() {
        String result = controller.createBlogPost("Test Title", "Test Content", "author@example.com");

        assertEquals("Success", result);
        verify(repository).save(any(BlogPost.class));
        verify(notificationService).notify("author@example.com", "Blog post created: Test Title");
        verify(logger).log("User author@example.com created post: Test Title");
    }

    @Test
    @DisplayName("Should save correct blog post data")
    void shouldSaveCorrectBlogPostData() {
        controller.createBlogPost("My Title", "My Content", "john@example.com");

        ArgumentCaptor<BlogPost> captor = ArgumentCaptor.forClass(BlogPost.class);
        verify(repository).save(captor.capture());

        BlogPost savedPost = captor.getValue();
        assertEquals("My Title", savedPost.title());
        assertEquals("My Content", savedPost.content());
        assertEquals("john@example.com", savedPost.author());
    }

    @Test
    @DisplayName("Should return error for empty title without calling services")
    void shouldReturnErrorForEmptyTitleWithoutCallingServices() {
        String result = controller.createBlogPost("", "Test Content", "author@example.com");

        assertEquals("Error: Title cannot be empty", result);
        verify(repository, never()).save(any());
        verify(notificationService, never()).notify(anyString(), anyString());
        verify(logger, never()).log(anyString());
    }

    @Test
    @DisplayName("Should return error for null title")
    void shouldReturnErrorForNullTitle() {
        String result = controller.createBlogPost(null, "Test Content", "author@example.com");

        assertEquals("Error: Title cannot be empty", result);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should return error for blank content")
    void shouldReturnErrorForBlankContent() {
        String result = controller.createBlogPost("Test Title", "   ", "author@example.com");

        assertEquals("Error: Content cannot be empty", result);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Should return error for null content")
    void shouldReturnErrorForNullContent() {
        String result = controller.createBlogPost("Test Title", null, "author@example.com");

        assertEquals("Error: Content cannot be empty", result);
    }

    @Test
    @DisplayName("Should verify all services are called in correct order")
    void shouldVerifyAllServicesAreCalledInCorrectOrder() {
        controller.createBlogPost("Title", "Content", "user@example.com");

        var inOrder = inOrder(repository, notificationService, logger);
        inOrder.verify(repository).save(any(BlogPost.class));
        inOrder.verify(notificationService).notify(anyString(), anyString());
        inOrder.verify(logger).log(anyString());
    }

    /*
     * ADVANTAGES OF THIS TEST:
     * 1. Can verify all interactions with dependencies (repository, notifier, logger)
     * 2. Can test in complete isolation using mocks
     * 3. No side effects (no actual emails, database calls, or file I/O)
     * 4. Fast execution
     * 5. Easy to test edge cases and error scenarios
     * 6. Can verify call order and number of invocations
     * 7. Clean test output (no console spam)
     */
}
