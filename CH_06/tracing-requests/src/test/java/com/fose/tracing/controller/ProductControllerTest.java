package com.fose.tracing.controller;

import com.fose.tracing.model.Product;
import com.fose.tracing.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for ProductController.
 *
 * These tests demonstrate the complete request flow from HTTP endpoint
 * through to the repository layer and back. Run these tests with debug
 * breakpoints to trace the execution path.
 *
 * The tests serve as documentation showing:
 * 1. What endpoints exist
 * 2. What data they expect
 * 3. What responses they return
 * 4. How errors are handled
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Should trace request flow: GET /api/products returns all products")
    void shouldGetAllProducts() {
        // When: Making a GET request to /api/products
        // TRACE: This triggers ProductController.getAllProducts()
        ResponseEntity<Product[]> response = restTemplate.getForEntity("/api/products", Product[].class);

        // Then: Response should be successful with products
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);

        /*
         * EXECUTION FLOW TRACED:
         * 1. HTTP GET /api/products
         * 2. ProductController.getAllProducts() [ENTRY POINT]
         * 3. ProductService.getAllProducts()
         * 4. ProductRepository.findAll()
         * 5. Returns List<Product>
         * 6. Service returns to Controller
         * 7. Controller wraps in ResponseEntity
         * 8. HTTP 200 OK with JSON array
         */
    }

    @Test
    @DisplayName("Should trace request flow: GET /api/products/{id} returns specific product")
    void shouldGetProductById() {
        // Given: A product exists with ID 1
        Long productId = 1L;

        // When: Making a GET request to /api/products/1
        // TRACE: This triggers ProductController.getProductById(1L)
        ResponseEntity<Product> response = restTemplate.getForEntity(
                "/api/products/" + productId,
                Product.class
        );

        // Then: Response should contain the product
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isEqualTo(productId);

        /*
         * EXECUTION FLOW TRACED:
         * 1. HTTP GET /api/products/1
         * 2. ProductController.getProductById(1L) [ENTRY POINT]
         *    - @PathVariable extracts 1 from URL
         * 3. ProductService.getProductById(1L)
         *    - Validates ID (business logic)
         * 4. ProductRepository.findById(1L)
         *    - Queries data store
         * 5. Returns Optional<Product>
         * 6. Service unwraps Optional and returns Product
         * 7. Controller wraps in ResponseEntity
         * 8. HTTP 200 OK with JSON object
         */
    }

    @Test
    @DisplayName("Should trace error flow: GET /api/products/{id} with non-existent ID returns 404")
    void shouldReturn404WhenProductNotFound() {
        // Given: A product ID that doesn't exist
        Long nonExistentId = 99999L;

        // When: Making a GET request for non-existent product
        ResponseEntity<Product> response = restTemplate.getForEntity(
                "/api/products/" + nonExistentId,
                Product.class
        );

        // Then: Should return 404 Not Found
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        /*
         * ERROR FLOW TRACED:
         * 1. HTTP GET /api/products/99999
         * 2. ProductController.getProductById(99999L)
         * 3. ProductService.getProductById(99999L)
         * 4. ProductRepository.findById(99999L)
         * 5. Returns Optional.empty()
         * 6. Service throws ProductNotFoundException
         * 7. Controller catches exception
         * 8. HTTP 404 Not Found
         *
         * This demonstrates how errors flow back through the layers!
         */
    }

    @Test
    @DisplayName("Should trace request flow: POST /api/products creates new product")
    void shouldCreateProduct() {
        // Given: A new product to create
        Product newProduct = new Product(
                null, // ID will be generated
                "Test Product",
                "A product for testing",
                new BigDecimal("99.99"),
                10
        );

        // When: Making a POST request to /api/products
        // TRACE: This triggers ProductController.createProduct()
        ResponseEntity<Product> response = restTemplate.postForEntity(
                "/api/products",
                newProduct,
                Product.class
        );

        // Then: Product should be created
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().id()).isNotNull();
        assertThat(response.getBody().name()).isEqualTo("Test Product");

        /*
         * EXECUTION FLOW TRACED:
         * 1. HTTP POST /api/products with JSON body
         * 2. Spring deserializes JSON to Product object
         * 3. ProductController.createProduct(product) [ENTRY POINT]
         *    - @RequestBody annotation triggers deserialization
         * 4. ProductService.createProduct(product)
         *    - Validates product data (business logic)
         *    - Sets ID to null to ensure new product
         * 5. ProductRepository.save(product)
         *    - Generates new ID
         *    - Stores product
         * 6. Returns saved Product with ID
         * 7. Service returns to Controller
         * 8. Controller wraps in ResponseEntity
         * 9. HTTP 201 Created with JSON object
         */
    }

    @Test
    @DisplayName("Should trace validation flow: POST /api/products with invalid data returns 400")
    void shouldReturn400WhenProductDataInvalid() {
        // Given: A product with invalid data (negative price)
        Product invalidProduct = new Product(
                null,
                "Invalid Product",
                "This has a negative price",
                new BigDecimal("-10.00"), // Invalid: negative price
                10
        );

        // When: Attempting to create invalid product
        ResponseEntity<Product> response = restTemplate.postForEntity(
                "/api/products",
                invalidProduct,
                Product.class
        );

        // Then: Should return 400 Bad Request
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        /*
         * VALIDATION FLOW TRACED:
         * 1. HTTP POST /api/products with invalid JSON
         * 2. ProductController.createProduct(invalidProduct)
         * 3. ProductService.createProduct(invalidProduct)
         * 4. ProductService.validateProduct(invalidProduct)
         *    - Detects negative price
         *    - Throws IllegalArgumentException
         * 5. Exception flows back to Controller
         * 6. Controller catches IllegalArgumentException
         * 7. HTTP 400 Bad Request
         *
         * Notice: Validation happens in the SERVICE layer (business logic),
         * not in the controller or repository!
         */
    }

    @Test
    @DisplayName("Should trace request flow: GET /api/products/search with query parameter")
    void shouldSearchProducts() {
        // When: Searching for products with "Laptop" in the name
        // TRACE: This triggers ProductController.searchProducts("Laptop")
        ResponseEntity<Product[]> response = restTemplate.getForEntity(
                "/api/products/search?name=Laptop",
                Product[].class
        );

        // Then: Should return matching products
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);

        /*
         * EXECUTION FLOW TRACED:
         * 1. HTTP GET /api/products/search?name=Laptop
         * 2. ProductController.searchProducts("Laptop") [ENTRY POINT]
         *    - @RequestParam extracts "Laptop" from query string
         * 3. ProductService.searchProducts("Laptop")
         *    - Validates search query
         * 4. ProductRepository.findByNameContaining("Laptop")
         *    - Filters products by name
         * 5. Returns List<Product> with matches
         * 6. Service returns to Controller
         * 7. Controller wraps in ResponseEntity
         * 8. HTTP 200 OK with JSON array
         *
         * This demonstrates how query parameters flow through the layers!
         */
    }
}
