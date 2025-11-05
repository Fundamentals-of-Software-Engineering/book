package com.fose;

/**
 * GOOD EXAMPLE: This controller follows Single Responsibility Principle
 * It only handles HTTP request logic and delegates other concerns
 * This makes it easy to test and maintain
 */
public class BlogPostController {

    private final BlogPostRepository repository;
    private final NotificationService notificationService;
    private final ActivityLogger logger;

    public BlogPostController(BlogPostRepository repository,
                             NotificationService notificationService,
                             ActivityLogger logger) {
        this.repository = repository;
        this.notificationService = notificationService;
        this.logger = logger;
    }

    public String createBlogPost(String title, String content, String author) {
        // Validate input (controller's responsibility)
        if (title == null || title.isBlank()) {
            return "Error: Title cannot be empty";
        }

        if (content == null || content.isBlank()) {
            return "Error: Content cannot be empty";
        }

        // Delegate to repository (separation of concerns)
        BlogPost post = new BlogPost(title, content, author);
        repository.save(post);

        // Delegate to notification service
        notificationService.notify(author, "Blog post created: " + title);

        // Delegate to logger
        logger.log("User " + author + " created post: " + title);

        return "Success";
    }
}
