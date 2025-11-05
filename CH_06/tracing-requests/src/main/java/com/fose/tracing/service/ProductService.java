package com.fose.tracing.service;

import com.fose.tracing.model.Product;
import com.fose.tracing.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Service layer for Product business logic.
 *
 * The service layer sits between the controller and repository.
 * It contains business logic and orchestrates calls to repositories.
 *
 * When tracing requests:
 * 1. The controller receives the HTTP request
 * 2. The controller calls the service (we are here!)
 * 3. The service calls the repository
 * 4. The response flows back up through the layers
 */
@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Get all products.
     * Notice how we log entry into the method - this helps trace the flow.
     */
    public List<Product> getAllProducts() {
        log.info("Service: getAllProducts called");
        List<Product> products = productRepository.findAll();
        log.info("Service: Returning {} products", products.size());
        return products;
    }

    /**
     * Get a product by ID with validation.
     */
    public Product getProductById(Long id) {
        log.info("Service: getProductById called with id: {}", id);

        // Business logic: validate the ID
        if (id == null || id <= 0) {
            log.error("Service: Invalid product id: {}", id);
            throw new IllegalArgumentException("Product ID must be a positive number");
        }

        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            log.error("Service: Product not found with id: {}", id);
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        log.info("Service: Successfully retrieved product: {}", product.get().name());
        return product.get();
    }

    /**
     * Create a new product with validation.
     */
    public Product createProduct(Product product) {
        log.info("Service: createProduct called for: {}", product.name());

        // Business logic: validate the product data
        validateProduct(product);

        // Ensure this is a new product by creating a new instance with null ID
        Product newProduct = new Product(
                null,  // No ID for new products
                product.name(),
                product.description(),
                product.price(),
                product.quantity()
        );

        Product savedProduct = productRepository.save(newProduct);
        log.info("Service: Product created successfully with id: {}", savedProduct.id());
        return savedProduct;
    }

    /**
     * Update an existing product.
     *
     * Since Product is immutable (a record), we create a new instance
     * with the updated fields instead of mutating the existing object.
     */
    public Product updateProduct(Long id, Product productDetails) {
        log.info("Service: updateProduct called for id: {}", id);

        // Verify the product exists
        Product existingProduct = getProductById(id);

        // Business logic: validate the update data
        validateProduct(productDetails);

        // Create a new Product with updated fields (records are immutable)
        Product updatedProduct = new Product(
                existingProduct.id(),  // Keep the same ID
                productDetails.name(),
                productDetails.description(),
                productDetails.price(),
                productDetails.quantity()
        );

        Product savedProduct = productRepository.save(updatedProduct);
        log.info("Service: Product updated successfully");
        return savedProduct;
    }

    /**
     * Delete a product by ID.
     */
    public void deleteProduct(Long id) {
        log.info("Service: deleteProduct called for id: {}", id);

        // Verify the product exists before deleting
        getProductById(id);

        boolean deleted = productRepository.deleteById(id);
        if (deleted) {
            log.info("Service: Product deleted successfully");
        }
    }

    /**
     * Search for products by name.
     */
    public List<Product> searchProducts(String name) {
        log.info("Service: searchProducts called with query: {}", name);

        if (name == null || name.trim().isEmpty()) {
            log.warn("Service: Empty search query, returning all products");
            return getAllProducts();
        }

        List<Product> results = productRepository.findByNameContaining(name);
        log.info("Service: Search returned {} results", results.size());
        return results;
    }

    /**
     * Business logic: validate product data.
     * This is a good example of business rules living in the service layer.
     */
    private void validateProduct(Product product) {
        if (product.name() == null || product.name().trim().isEmpty()) {
            log.error("Service: Validation failed - product name is required");
            throw new IllegalArgumentException("Product name is required");
        }

        if (product.price() == null || product.price().compareTo(BigDecimal.ZERO) < 0) {
            log.error("Service: Validation failed - invalid price: {}", product.price());
            throw new IllegalArgumentException("Product price must be zero or positive");
        }

        if (product.quantity() == null || product.quantity() < 0) {
            log.error("Service: Validation failed - invalid quantity: {}", product.quantity());
            throw new IllegalArgumentException("Product quantity must be zero or positive");
        }

        log.debug("Service: Product validation passed");
    }

    /**
     * Custom exception for product not found scenarios.
     */
    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
}
