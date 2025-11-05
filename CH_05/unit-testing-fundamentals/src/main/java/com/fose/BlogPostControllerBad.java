package com.fose;

import java.util.ArrayList;
import java.util.List;

/**
 * BAD EXAMPLE: This controller violates Single Responsibility Principle
 * It handles HTTP requests, database operations, AND email notifications
 * This makes it difficult to test and maintain
 */
public class BlogPostControllerBad {

    private final List<BlogPost> database = new ArrayList<>();

    public String createBlogPost(String title, String content, String author) {
        // Validate input
        if (title == null || title.isBlank()) {
            return "Error: Title cannot be empty";
        }

        if (content == null || content.isBlank()) {
            return "Error: Content cannot be empty";
        }

        // Save to database (mixing business logic with data access)
        BlogPost post = new BlogPost(title, content, author);
        database.add(post);

        // Send email notification (mixing concerns)
        sendEmail(author, "Blog post created: " + title);

        // Log the action (another responsibility)
        logActivity("User " + author + " created post: " + title);

        return "Success";
    }

    private void sendEmail(String to, String message) {
        // Simulates email sending - hard to test!
        System.out.println("Sending email to " + to + ": " + message);
    }

    private void logActivity(String message) {
        // Simulates logging - another dependency to worry about
        System.out.println("LOG: " + message);
    }

    public int getPostCount() {
        return database.size();
    }
}
