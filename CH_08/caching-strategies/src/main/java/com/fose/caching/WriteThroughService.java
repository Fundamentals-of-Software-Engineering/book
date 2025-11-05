package com.fose.caching;

/**
 * Write-Through Strategy - Example from Chapter 8
 *
 * Every write updates both cache and database simultaneously:
 * - Ensures cache and database are always in sync
 * - Provides strong consistency
 *
 * Pros: Strong consistency, reads are always fast
 * Cons: Slower writes (must wait for both cache and database)
 */
public class WriteThroughService {
    private final SimpleCache cache;
    private final SimpleDatabase database;

    public WriteThroughService(SimpleCache cache, SimpleDatabase database) {
        this.cache = cache;
        this.database = database;
    }

    public BlogPost getPostById(Long id) {
        // Check cache (should always be present if written through this service)
        BlogPost cached = cache.get(id);
        if (cached != null) {
            return cached;
        }

        // Fallback to database
        return database.findById(id);
    }

    /**
     * Example from the chapter - updatePost with write-through
     * Updates both database and cache synchronously
     */
    public void updatePost(BlogPost post) {
        // Update database first
        database.save(post);
        // Then update cache (both operations are synchronous)
        cache.put(post.getId(), post);
    }
}
