package com.fose.refactoring.after;

import com.fose.refactoring.model.Order;
import com.fose.refactoring.model.OrderItem;

/**
 * AFTER REFACTORING
 *
 * This refactored version breaks down the complex calculateTotal method
 * into smaller, focused methods that each do one thing well.
 *
 * Improvements:
 * 1. Each method has a single, clear responsibility
 * 2. Business logic is explicit and easy to understand
 * 3. Each piece can be tested independently
 * 4. Magic numbers are now named constants with clear meaning
 * 5. The main method reads like a recipe - easy to follow
 * 6. Future changes are easier (just modify the relevant method)
 *
 * The behavior is EXACTLY the same as the "before" version.
 * The tests prove this!
 */
public class OrderService {

    // Named constants make the business rules explicit
    private static final double PREMIUM_CUSTOMER_DISCOUNT = 0.10; // 10%
    private static final double FREE_SHIPPING_THRESHOLD = 100.00;
    private static final double STANDARD_SHIPPING_COST = 10.00;
    private static final double STANDARD_TAX_RATE = 0.08; // 8%
    private static final double HIGH_VALUE_TAX_RATE = 0.15; // 15%
    private static final double HIGH_VALUE_TAX_THRESHOLD = 500.00;
    private static final double PREMIUM_EXPRESS_THRESHOLD = 50.00;
    private static final double REGULAR_EXPRESS_THRESHOLD = 200.00;
    private static final String EXPRESS_DISCOUNT_CODE = "EXPRESS";

    /**
     * Calculate the total for an order.
     *
     * Now this method is easy to read - it's like a recipe!
     * Each step is clearly named and does exactly what it says.
     */
    public double calculateTotal(Order order) {
        double subtotal = calculateSubtotal(order);
        double afterDiscounts = applyDiscounts(subtotal, order);
        double shipping = calculateShipping(afterDiscounts);
        double subtotalWithShipping = afterDiscounts + shipping;
        double tax = calculateTax(subtotalWithShipping);
        double total = subtotalWithShipping + tax;

        return roundToTwoDecimals(total);
    }

    /**
     * Calculate the subtotal from all order items.
     * Single responsibility: sum up item prices.
     */
    private double calculateSubtotal(Order order) {
        return order.getItems().stream()
                .mapToDouble(item -> item.price() * item.quantity())
                .sum();
    }

    /**
     * Apply all applicable discounts to the subtotal.
     * Single responsibility: handle discount logic.
     */
    private double applyDiscounts(double subtotal, Order order) {
        double total = subtotal;

        // Apply premium customer discount
        if (order.isPremiumCustomer()) {
            total = applyPremiumDiscount(total);
        }

        // Apply promotional discount code
        if (order.hasDiscountCode()) {
            total = applyPromotionalDiscount(total, order);
        }

        return total;
    }

    /**
     * Apply the premium customer discount.
     */
    private double applyPremiumDiscount(double amount) {
        return amount * (1 - PREMIUM_CUSTOMER_DISCOUNT);
    }

    /**
     * Apply a promotional discount code.
     */
    private double applyPromotionalDiscount(double amount, Order order) {
        double discountRate = order.getDiscountRate();
        return amount * (1 - discountRate);
    }

    /**
     * Calculate shipping cost based on order subtotal.
     * Single responsibility: determine shipping cost.
     *
     * Business rule: Orders over $100 get free shipping
     */
    private double calculateShipping(double subtotal) {
        if (subtotal >= FREE_SHIPPING_THRESHOLD) {
            return 0.0;
        }
        return STANDARD_SHIPPING_COST;
    }

    /**
     * Calculate tax based on order total.
     * Single responsibility: determine tax amount.
     *
     * Business rule: Different tax rates for different order values
     */
    private double calculateTax(double amount) {
        if (amount < HIGH_VALUE_TAX_THRESHOLD) {
            return amount * STANDARD_TAX_RATE;
        }
        return amount * HIGH_VALUE_TAX_RATE;
    }

    /**
     * Round a dollar amount to two decimal places.
     */
    private double roundToTwoDecimals(double amount) {
        return Math.round(amount * 100.0) / 100.0;
    }

    /**
     * Determine if an order qualifies for express shipping.
     *
     * This refactored version is much clearer than the original.
     * Each condition is extracted to a well-named method.
     */
    public boolean qualifiesForExpressShipping(Order order) {
        return qualifiesAsPremiumCustomer(order)
                || qualifiesAsRegularCustomer(order)
                || hasExpressCode(order);
    }

    /**
     * Premium customers qualify with orders over $50.
     */
    private boolean qualifiesAsPremiumCustomer(Order order) {
        if (!order.isPremiumCustomer()) {
            return false;
        }
        double subtotal = calculateSubtotal(order);
        return subtotal > PREMIUM_EXPRESS_THRESHOLD;
    }

    /**
     * Regular customers qualify with orders over $200.
     */
    private boolean qualifiesAsRegularCustomer(Order order) {
        if (order.isPremiumCustomer()) {
            return false;
        }
        double subtotal = calculateSubtotal(order);
        return subtotal > REGULAR_EXPRESS_THRESHOLD;
    }

    /**
     * Anyone with the EXPRESS discount code qualifies.
     */
    private boolean hasExpressCode(Order order) {
        return EXPRESS_DISCOUNT_CODE.equals(order.getDiscountCode());
    }
}
