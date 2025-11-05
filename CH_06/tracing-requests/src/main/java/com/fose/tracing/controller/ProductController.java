package com.fose.tracing.controller;

import com.fose.tracing.model.Product;
import com.fose.tracing.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Product endpoints.
 *
 * This is an APPLICATION ENTRY POINT - a "door" into the application.
 * When exploring a Spring Boot application, look for @RestController or @Controller
 * annotations to find the entry points for HTTP requests.
 *
 * Each @GetMapping, @PostMapping, etc. represents an API endpoint that clients
 * can call. Set breakpoints on these methods to trace how requests flow through
 * the application.
 *
 * Request Flow:
 * 1. HTTP Request arrives (e.g., GET /api/products)
 * 2. Spring routes to appropriate controller method (e.g., getAllProducts)
 * 3. Controller calls service layer
 * 4. Service calls repository layer
 * 5. Data flows back up: Repository -> Service -> Controller -> HTTP Response
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/products - Get all products
     *
     * Entry point #1: This method is called when a GET request is made to /api/products
     * Set a breakpoint here and make a request to trace the full flow.
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Controller: GET /api/products - getAllProducts called");

        List<Product> products = productService.getAllProducts();

        log.info("Controller: Returning {} products with status 200 OK", products.size());
        return ResponseEntity.ok(products);
    }

    /**
     * GET /api/products/{id} - Get a specific product by ID
     *
     * Entry point #2: This is called when a GET request includes a product ID
     * Example: GET /api/products/1
     *
     * The @PathVariable annotation extracts the {id} from the URL path.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        log.info("Controller: GET /api/products/{} - getProductById called", id);

        try {
            Product product = productService.getProductById(id);
            log.info("Controller: Returning product with status 200 OK");
            return ResponseEntity.ok(product);
        } catch (ProductService.ProductNotFoundException e) {
            log.warn("Controller: Product not found, returning 404");
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Controller: Invalid request, returning 400");
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * POST /api/products - Create a new product
     *
     * Entry point #3: This is called when a POST request creates a new product
     * The @RequestBody annotation deserializes the JSON request body into a Product object.
     *
     * Try this with an API testing tool like Postman:
     * POST /api/products
     * Body: {"name": "Monitor", "description": "4K Monitor", "price": 399.99, "quantity": 15}
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("Controller: POST /api/products - createProduct called for: {}", product.name());

        try {
            Product createdProduct = productService.createProduct(product);
            log.info("Controller: Product created successfully with id: {}, returning 201 Created",
                    createdProduct.id());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        } catch (IllegalArgumentException e) {
            log.warn("Controller: Validation failed: {} - returning 400", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /api/products/{id} - Update an existing product
     *
     * Entry point #4: This updates an existing product
     * Combines @PathVariable (for the ID) and @RequestBody (for the update data)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        log.info("Controller: PUT /api/products/{} - updateProduct called", id);

        try {
            Product updatedProduct = productService.updateProduct(id, product);
            log.info("Controller: Product updated successfully, returning 200 OK");
            return ResponseEntity.ok(updatedProduct);
        } catch (ProductService.ProductNotFoundException e) {
            log.warn("Controller: Product not found, returning 404");
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            log.warn("Controller: Validation failed: {} - returning 400", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/products/{id} - Delete a product
     *
     * Entry point #5: This deletes a product by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Controller: DELETE /api/products/{} - deleteProduct called", id);

        try {
            productService.deleteProduct(id);
            log.info("Controller: Product deleted successfully, returning 204 No Content");
            return ResponseEntity.noContent().build();
        } catch (ProductService.ProductNotFoundException e) {
            log.warn("Controller: Product not found, returning 404");
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/products/search?name=query - Search for products by name
     *
     * Entry point #6: This searches for products matching a query
     * The @RequestParam annotation extracts query parameters from the URL
     * Example: GET /api/products/search?name=laptop
     */
    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        log.info("Controller: GET /api/products/search?name={} - searchProducts called", name);

        List<Product> results = productService.searchProducts(name);

        log.info("Controller: Returning {} search results with status 200 OK", results.size());
        return ResponseEntity.ok(results);
    }

    /**
     * Exception handler for this controller.
     * Catches any unexpected exceptions and returns a 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        log.error("Controller: Unexpected error occurred", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An unexpected error occurred");
    }
}
