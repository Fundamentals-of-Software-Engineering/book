package com.fose.caching;

/**
 * Cache-Aside (Lazy Loading) Strategy - Example from Chapter 8
 *
 * Application code manages both cache and database:
 * 1. Check cache first
 * 2. On miss, load from database and store in cache
 * 3. On hit, return from cache
 *
 * Pros: Fine-grained control, only cache what's needed
 * Cons: More complex application code, first request is always slow
 */
public class CacheAsideService {
    private final SimpleCache cache;
    private final SimpleDatabase database;

    public CacheAsideService(SimpleCache cache, SimpleDatabase database) {
        this.cache = cache;
        this.database = database;
    }

    /**
     * Example from the chapter - getPostById with cache-aside
     */
    public BlogPost getPostById(Long id) {
        // Check cache first
        BlogPost cached = cache.get(id);
        if (cached != null) {
            return cached;
        }

        // Cache miss - load from database
        BlogPost post = database.findById(id);
        if (post != null) {
            cache.put(id, post);
        }
        return post;
    }

    /**
     * Write operations go directly to database
     * Cache is only updated on subsequent reads
     */
    public void updatePost(BlogPost post) {
        database.save(post);
        // Note: Cache is NOT updated here
        // It will be updated on next read (lazy loading)
    }
}
