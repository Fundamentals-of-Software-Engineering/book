package com.fose.caching;

import java.util.HashMap;
import java.util.Map;

/**
 * Simulates a database for demonstration purposes
 * Tracks read and write counts to show performance impact
 */
public class SimpleDatabase {
    private final Map<Long, BlogPost> storage = new HashMap<>();
    private int readCount = 0;
    private int writeCount = 0;

    public BlogPost findById(Long id) {
        readCount++;
        // Simulate slow database access
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return storage.get(id);
    }

    public void save(BlogPost post) {
        writeCount++;
        // Simulate slow database write
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        storage.put(post.getId(), post);
    }

    public int getReadCount() {
        return readCount;
    }

    public int getWriteCount() {
        return writeCount;
    }

    public void clear() {
        storage.clear();
        readCount = 0;
        writeCount = 0;
    }
}
