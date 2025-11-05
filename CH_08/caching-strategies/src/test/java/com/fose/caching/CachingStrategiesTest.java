package com.fose.caching;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Demonstrates the three caching strategies from Chapter 8:
 * 1. Cache-Aside (Lazy Loading)
 * 2. Write-Through
 * 3. Write-Behind (Write-Back)
 */
class CachingStrategiesTest {
    private SimpleCache cache;
    private SimpleDatabase database;

    @BeforeEach
    void setUp() {
        cache = new SimpleCache();
        database = new SimpleDatabase();
    }

    @Test
    void cacheAside_LoadsOnFirstAccess() {
        CacheAsideService service = new CacheAsideService(cache, database);

        // Seed database with a post
        BlogPost post = new BlogPost(1L, "Test Post", "Content");
        database.save(post);

        // First access - cache miss, loads from database
        BlogPost result1 = service.getPostById(1L);
        assertEquals("Test Post", result1.getTitle());
        assertEquals(0, cache.getHits());
        assertEquals(1, cache.getMisses());
        assertEquals(1, database.getReadCount()); // getPostById caused 1 read

        // Second access - cache hit
        BlogPost result2 = service.getPostById(1L);
        assertEquals("Test Post", result2.getTitle());
        assertEquals(1, cache.getHits());
        assertEquals(1, cache.getMisses());
        assertEquals(1, database.getReadCount()); // No additional database read

        System.out.println("✓ Cache-Aside: First request is slow, subsequent requests are fast");
    }

    @Test
    void cacheAside_DoesNotUpdateCacheOnWrite() {
        CacheAsideService service = new CacheAsideService(cache, database);

        // Create and cache a post
        BlogPost post = new BlogPost(1L, "Original", "Content");
        database.save(post);
        service.getPostById(1L); // Load into cache

        // Update the post (cache is NOT updated)
        BlogPost updated = new BlogPost(1L, "Updated", "New Content");
        service.updatePost(updated);

        // Cache still has old version
        BlogPost cached = service.getPostById(1L);
        assertEquals("Original", cached.getTitle()); // Stale data!

        System.out.println("✗ Cache-Aside: Can serve stale data after updates");
    }

    @Test
    void writeThrough_KeepsCacheAndDatabaseInSync() {
        WriteThroughService service = new WriteThroughService(cache, database);

        // Write a post (updates both cache and database)
        BlogPost post = new BlogPost(1L, "Test Post", "Content");
        service.updatePost(post);

        // Read from cache is immediate
        BlogPost cached = service.getPostById(1L);
        assertEquals("Test Post", cached.getTitle());
        assertEquals(1, cache.getHits());

        // Database also has the data
        BlogPost fromDb = database.findById(1L);
        assertEquals("Test Post", fromDb.getTitle());

        System.out.println("✓ Write-Through: Cache and database always in sync");
    }

    @Test
    void writeThrough_SlowerWrites() {
        WriteThroughService service = new WriteThroughService(cache, database);

        long startTime = System.currentTimeMillis();

        // Write-through updates both cache and database (slower)
        BlogPost post = new BlogPost(1L, "Test Post", "Content");
        service.updatePost(post);

        long writeTime = System.currentTimeMillis() - startTime;

        // Write took time because it updated both systems
        assertTrue(writeTime >= 10); // Database has 10ms delay

        System.out.println("✗ Write-Through: Writes are slower (waited " + writeTime + "ms)");
    }

    @Test
    void writeBehind_FastWrites() throws InterruptedException {
        WriteBehindService service = new WriteBehindService(cache, database);

        long startTime = System.currentTimeMillis();

        // Write-behind updates cache immediately (fast!)
        BlogPost post = new BlogPost(1L, "Test Post", "Content");
        service.updatePost(post);

        long writeTime = System.currentTimeMillis() - startTime;

        // Write was fast because database update is async
        assertTrue(writeTime < 10); // Much faster than database delay

        // Cache has the data immediately
        BlogPost cached = service.getPostById(1L);
        assertEquals("Test Post", cached.getTitle());

        // Wait for async database update
        Thread.sleep(50);
        service.shutdown();

        // Database eventually gets the data
        BlogPost fromDb = database.findById(1L);
        assertEquals("Test Post", fromDb.getTitle());

        System.out.println("✓ Write-Behind: Fast writes (" + writeTime + "ms), eventual consistency");
    }

    @Test
    void writeBehind_TemporaryInconsistency() {
        WriteBehindService service = new WriteBehindService(cache, database);

        // Write to cache (database update is async)
        BlogPost post = new BlogPost(1L, "Test Post", "Content");
        service.updatePost(post);

        // Cache has the data
        BlogPost cached = service.getPostById(1L);
        assertNotNull(cached);

        // But database might not have it yet (temporary inconsistency)
        // Note: In a real scenario, database might be null here
        // For this test, we just demonstrate the async nature

        service.shutdown();

        System.out.println("⚠ Write-Behind: Temporary inconsistency between cache and database");
    }

    @Test
    void comparingStrategies_PerformanceTradeoffs() {
        System.out.println("\n=== Caching Strategy Comparison ===");
        System.out.println("Cache-Aside:");
        System.out.println("  + Fine-grained control");
        System.out.println("  + Only cache what's needed");
        System.out.println("  - First request is slow");
        System.out.println("  - Can serve stale data");

        System.out.println("\nWrite-Through:");
        System.out.println("  + Strong consistency");
        System.out.println("  + Reads are always fast");
        System.out.println("  - Slower writes");
        System.out.println("  - May cache unused data");

        System.out.println("\nWrite-Behind:");
        System.out.println("  + Very fast writes");
        System.out.println("  + Good for write-heavy workloads");
        System.out.println("  - Risk of data loss");
        System.out.println("  - Temporary inconsistency");
    }
}
