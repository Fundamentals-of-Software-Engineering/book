package com.fose.caching;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple in-memory cache implementation for demonstration
 */
public class SimpleCache {
    private final Map<Long, BlogPost> cache = new HashMap<>();
    private int hits = 0;
    private int misses = 0;

    public BlogPost get(Long id) {
        BlogPost post = cache.get(id);
        if (post != null) {
            hits++;
        } else {
            misses++;
        }
        return post;
    }

    public void put(Long id, BlogPost post) {
        cache.put(id, post);
    }

    public void clear() {
        cache.clear();
        hits = 0;
        misses = 0;
    }

    public int getHits() {
        return hits;
    }

    public int getMisses() {
        return misses;
    }
}
