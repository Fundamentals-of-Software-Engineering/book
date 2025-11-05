package com.fose.good;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * GOOD EXAMPLE: This class has meaningful test coverage.
 * Tests verify actual behavior, not just execute code.
 */
public class OrderProcessor {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.08");
    private static final BigDecimal DISCOUNT_THRESHOLD = new BigDecimal("100.00");
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10");

    public BigDecimal calculateTotal(BigDecimal subtotal) {
        if (subtotal == null || subtotal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Subtotal cannot be null or negative");
        }

        BigDecimal total = subtotal;

        // Apply discount if eligible
        if (subtotal.compareTo(DISCOUNT_THRESHOLD) >= 0) {
            BigDecimal discount = subtotal.multiply(DISCOUNT_RATE);
            total = subtotal.subtract(discount);
        }

        // Add tax
        BigDecimal tax = total.multiply(TAX_RATE);
        total = total.add(tax);

        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public boolean isEligibleForFreeShipping(BigDecimal orderTotal) {
        return orderTotal != null && orderTotal.compareTo(new BigDecimal("50.00")) >= 0;
    }

    public String getShippingMethod(BigDecimal orderTotal, boolean expressRequested) {
        if (isEligibleForFreeShipping(orderTotal)) {
            return expressRequested ? "Express (Free)" : "Standard (Free)";
        }
        return expressRequested ? "Express ($15)" : "Standard ($5)";
    }
}
