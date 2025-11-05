package com.fose.tracing.model;

import java.math.BigDecimal;

/**
 * Domain model representing a product in the system.
 *
 * This is a Java record - a modern, concise way to define immutable data carriers.
 * Records automatically provide:
 * - Canonical constructor
 * - Getters (without the "get" prefix - just use fieldName())
 * - equals(), hashCode(), and toString()
 * - Immutability (all fields are final)
 *
 * When exploring a codebase, domain models help you understand
 * the core business entities and their properties.
 *
 * Benefits of using records for domain models:
 * - Less boilerplate code
 * - Immutability by default (safer in concurrent environments)
 * - Clear intent: this is a data carrier, not behavior-heavy class
 * - Modern Java 21 feature
 */
public record Product(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
    /**
     * Compact constructor for validation.
     * This runs before field initialization.
     */
    public Product {
        // Note: Records don't need explicit field assignments in the constructor
        // The canonical constructor is automatically generated
    }

    /**
     * Convenience constructor for creating products without an ID.
     * Useful when creating new products before they're saved.
     */
    public Product(String name, String description, BigDecimal price, Integer quantity) {
        this(null, name, description, price, quantity);
    }
}
