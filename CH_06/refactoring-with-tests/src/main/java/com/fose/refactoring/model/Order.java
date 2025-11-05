package com.fose.refactoring.model;

import java.util.List;

/**
 * Represents an order in the system.
 *
 * Converted to a Java record for immutability.
 * Immutable domain models are safer and prevent accidental modifications.
 *
 * This is the domain model used by both the "before" and "after" versions.
 */
public record Order(
        List<OrderItem> items,
        boolean premiumCustomer,
        String discountCode,
        double discountRate
) {
    /**
     * Compact constructor for defensive copying of the list.
     * Makes a copy to ensure the internal list can't be modified from outside.
     */
    public Order {
        items = List.copyOf(items); // Defensive copy - creates unmodifiable list
    }

    /**
     * Convenience constructor for orders with just items (no discounts).
     */
    public Order(List<OrderItem> items) {
        this(items, false, null, 0.0);
    }

    /**
     * Convenience constructor for premium customer orders.
     */
    public Order(List<OrderItem> items, boolean premiumCustomer) {
        this(items, premiumCustomer, null, 0.0);
    }

    /**
     * Helper method to check if this order has a discount code applied.
     *
     * @return true if a discount code is present and not empty
     */
    public boolean hasDiscountCode() {
        return discountCode != null && !discountCode.isEmpty();
    }

    /**
     * Compatibility method for code using old getter naming.
     * Records use field names directly, but we can add this for clarity.
     */
    public boolean isPremiumCustomer() {
        return premiumCustomer;
    }

    /**
     * Compatibility method for old code using getItems().
     */
    public List<OrderItem> getItems() {
        return items;
    }

    /**
     * Compatibility method for old code using getDiscountCode().
     */
    public String getDiscountCode() {
        return discountCode;
    }

    /**
     * Compatibility method for old code using getDiscountRate().
     */
    public double getDiscountRate() {
        return discountRate;
    }
}
