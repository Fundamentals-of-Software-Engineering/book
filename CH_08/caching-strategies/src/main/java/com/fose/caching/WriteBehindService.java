package com.fose.caching;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Write-Behind (Write-Back) Strategy - Example from Chapter 8
 *
 * Writes to cache immediately, database update is asynchronous:
 * - Fast write performance
 * - Risk of data loss if cache fails before database update
 *
 * Pros: Very fast writes
 * Cons: Temporary inconsistency, risk of data loss
 */
public class WriteBehindService {
    private final SimpleCache cache;
    private final SimpleDatabase database;
    private final ExecutorService asyncQueue;

    public WriteBehindService(SimpleCache cache, SimpleDatabase database) {
        this.cache = cache;
        this.database = database;
        this.asyncQueue = Executors.newSingleThreadExecutor();
    }

    public BlogPost getPostById(Long id) {
        // Check cache first
        BlogPost cached = cache.get(id);
        if (cached != null) {
            return cached;
        }

        // Fallback to database
        return database.findById(id);
    }

    /**
     * Example from the chapter - updatePost with write-behind
     * Updates cache immediately, database asynchronously
     */
    public void updatePost(BlogPost post) {
        // Update cache immediately (fast!)
        cache.put(post.getId(), post);

        // Schedule database update asynchronously
        asyncQueue.submit(() -> database.save(post));
    }

    /**
     * Shutdown the async queue
     */
    public void shutdown() {
        asyncQueue.shutdown();
    }
}
