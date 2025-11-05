package com.fose.refactoring.before;

import com.fose.refactoring.model.Order;
import com.fose.refactoring.model.OrderItem;

/**
 * BEFORE REFACTORING
 *
 * This OrderService contains a complex calculateTotal method that does too much.
 * It's hard to understand, hard to test, and hard to modify.
 *
 * Problems with this code:
 * 1. Single method does too many things (violates Single Responsibility Principle)
 * 2. Complex nested logic is hard to follow
 * 3. No clear separation of concerns
 * 4. Hard to test individual pieces of logic
 * 5. Magic numbers (0.1, 0.15, 100, 500) are not explained
 * 6. Business rules are buried in implementation details
 *
 * When you encounter code like this in an existing codebase:
 * - Don't refactor immediately!
 * - First, write tests that document the current behavior
 * - Then refactor with confidence, knowing tests will catch any mistakes
 */
public class OrderService {

    /**
     * Calculate the total for an order.
     *
     * This method is doing WAY too much:
     * - Calculating subtotal
     * - Applying customer-level discounts
     * - Applying promotional discounts
     * - Calculating shipping
     * - Calculating tax
     *
     * Can you understand what's happening just by reading it?
     */
    public double calculateTotal(Order order) {
        double total = 0;

        // Calculate subtotal from items
        for (OrderItem item : order.getItems()) {
            total += item.price() * item.quantity();
        }

        // Apply customer discount if they're a premium member
        if (order.isPremiumCustomer()) {
            total = total * 0.9; // 10% discount for premium customers
        }

        // Apply discount code if present
        if (order.hasDiscountCode()) {
            double discountRate = order.getDiscountRate();
            total = total * (1 - discountRate);
        }

        // Calculate shipping (free for orders over $100, otherwise $10)
        double shipping = 0;
        if (total < 100) {
            shipping = 10;
        }

        // Add shipping to total
        total += shipping;

        // Calculate tax (different rates for different order amounts)
        double tax = 0;
        if (total < 500) {
            tax = total * 0.08; // 8% tax for orders under $500
        } else {
            tax = total * 0.15; // 15% tax for orders $500 and above
        }

        // Add tax to get final total
        total += tax;

        return Math.round(total * 100.0) / 100.0; // Round to 2 decimal places
    }

    /**
     * This method also suffers from complexity.
     * It determines if an order qualifies for express shipping based on multiple criteria.
     */
    public boolean qualifiesForExpressShipping(Order order) {
        double total = 0;
        for (OrderItem item : order.getItems()) {
            total += item.price() * item.quantity();
        }

        // Express shipping rules:
        // - Premium customers with orders over $50
        // - Regular customers with orders over $200
        // - OR if they have a special express code
        if (order.isPremiumCustomer() && total > 50) {
            return true;
        }

        if (!order.isPremiumCustomer() && total > 200) {
            return true;
        }

        if (order.getDiscountCode() != null && order.getDiscountCode().equals("EXPRESS")) {
            return true;
        }

        return false;
    }
}
