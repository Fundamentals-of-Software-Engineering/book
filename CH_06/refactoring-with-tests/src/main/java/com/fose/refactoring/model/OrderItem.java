package com.fose.refactoring.model;

/**
 * Represents a single item in an order.
 *
 * Converted to a Java record for immutability and conciseness.
 * Records are perfect for simple data carriers like order items.
 */
public record OrderItem(
        String productName,
        double price,
        int quantity
) {
    // Compact constructor for validation if needed
    public OrderItem {
        // Records automatically generate constructor, getters, equals, hashCode, toString
    }
}
