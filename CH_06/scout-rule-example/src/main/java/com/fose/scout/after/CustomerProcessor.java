package com.fose.scout.after;

import java.util.List;

/**
 * AFTER APPLYING THE SCOUT RULE
 *
 * This code does exactly the same thing as the "before" version,
 * but it's been improved through small, incremental changes:
 *
 * Improvements made:
 * 1. ✅ Better method names (prcs → processCustomers, chk → qualifiesForDiscount)
 * 2. ✅ Added JavaDoc documentation
 * 3. ✅ Used generic types (List<Customer> instead of raw List)
 * 4. ✅ Modern enhanced for-loop
 * 5. ✅ Removed dead code (oldMethod, commented email code)
 * 6. ✅ Named constant with clear explanation (DISCOUNT_THRESHOLD)
 * 7. ✅ Simplified boolean logic
 *
 * The Scout Rule: "Always leave the code better than you found it."
 *
 * These small improvements didn't require a major refactoring effort,
 * but they make the code significantly more maintainable!
 */
public class CustomerProcessor {

    /**
     * Minimum purchase amount to qualify as a premium customer.
     * Changed from $100 to $1000 in 2024 as part of loyalty program update.
     */
    private static final int PREMIUM_CUSTOMER_THRESHOLD = 1000;

    /**
     * Minimum purchase amount to qualify for discounts.
     * Customers must also be active to receive discounts.
     */
    private static final int DISCOUNT_THRESHOLD = 500;

    /**
     * Process a list of customers and update their premium status.
     *
     * For each active customer, checks if their total purchases exceed
     * the premium threshold. If so, marks them as premium.
     *
     * @param customers The list of customers to process
     */
    public void processCustomers(List<Customer> customers) {
        for (Customer customer : customers) {
            // Only process active customers
            if (customer.isActive()) {
                updatePremiumStatus(customer);
            }
        }
    }

    /**
     * Update a customer's premium status based on their purchase history.
     *
     * @param customer The customer to update
     */
    private void updatePremiumStatus(Customer customer) {
        double totalPurchases = customer.getTotalPurchases();

        if (totalPurchases > PREMIUM_CUSTOMER_THRESHOLD) {
            customer.setPremium(true);
        }
    }

    /**
     * Check if a customer qualifies for a discount.
     *
     * Customers qualify for discounts if they:
     * - Have total purchases over the discount threshold
     * - Have an active account status
     *
     * @param customer The customer to check
     * @return true if the customer qualifies for a discount, false otherwise
     */
    public boolean qualifiesForDiscount(Customer customer) {
        return customer.getTotalPurchases() > DISCOUNT_THRESHOLD
                && customer.isActive();
    }
}
