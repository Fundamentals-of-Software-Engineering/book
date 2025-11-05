package com.fose.tracing.repository;

import com.fose.tracing.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Repository layer for Product data access.
 *
 * In a real application, this would interact with a database.
 * For this example, we're using an in-memory store to keep things simple.
 *
 * When tracing requests, the repository is typically the final layer before
 * data persistence. Set breakpoints here to see what data is being saved or retrieved.
 */
@Repository
public class ProductRepository {

    private static final Logger log = LoggerFactory.getLogger(ProductRepository.class);

    // In-memory data store
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ProductRepository() {
        // Initialize with some sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        save(new Product(null, "Laptop", "High-performance laptop", new BigDecimal("1299.99"), 10));
        save(new Product(null, "Mouse", "Wireless mouse", new BigDecimal("29.99"), 50));
        save(new Product(null, "Keyboard", "Mechanical keyboard", new BigDecimal("89.99"), 25));
    }

    /**
     * Find all products in the repository.
     * Notice the logging - this helps trace the execution flow.
     */
    public List<Product> findAll() {
        log.info("Repository: Finding all products");
        List<Product> allProducts = new ArrayList<>(products.values());
        log.info("Repository: Found {} products", allProducts.size());
        return allProducts;
    }

    /**
     * Find a product by its ID.
     * The Optional return type indicates that the product might not exist.
     */
    public Optional<Product> findById(Long id) {
        log.info("Repository: Finding product with id: {}", id);
        Optional<Product> product = Optional.ofNullable(products.get(id));
        if (product.isPresent()) {
            log.info("Repository: Product found: {}", product.get().name());
        } else {
            log.warn("Repository: Product with id {} not found", id);
        }
        return product;
    }

    /**
     * Save a new product or update an existing one.
     *
     * Since Product is now a record (immutable), we create a new instance
     * with the generated ID instead of mutating the existing object.
     */
    public Product save(Product product) {
        Product productToSave;

        if (product.id() == null) {
            // Create new product with generated ID
            Long newId = idCounter.getAndIncrement();
            productToSave = new Product(
                    newId,
                    product.name(),
                    product.description(),
                    product.price(),
                    product.quantity()
            );
            log.info("Repository: Creating new product with id: {}", productToSave.id());
        } else {
            // Product already has an ID, save as-is
            productToSave = product;
            log.info("Repository: Updating product with id: {}", productToSave.id());
        }

        products.put(productToSave.id(), productToSave);
        log.info("Repository: Product saved successfully: {}", productToSave);
        return productToSave;
    }

    /**
     * Delete a product by ID.
     */
    public boolean deleteById(Long id) {
        log.info("Repository: Deleting product with id: {}", id);
        Product removed = products.remove(id);
        boolean wasDeleted = removed != null;
        if (wasDeleted) {
            log.info("Repository: Product deleted successfully");
        } else {
            log.warn("Repository: Could not delete - product with id {} not found", id);
        }
        return wasDeleted;
    }

    /**
     * Find products by name (partial match).
     */
    public List<Product> findByNameContaining(String name) {
        log.info("Repository: Searching for products containing: {}", name);
        List<Product> matches = products.values().stream()
                .filter(p -> p.name().toLowerCase().contains(name.toLowerCase()))
                .toList();
        log.info("Repository: Found {} matching products", matches.size());
        return matches;
    }
}
