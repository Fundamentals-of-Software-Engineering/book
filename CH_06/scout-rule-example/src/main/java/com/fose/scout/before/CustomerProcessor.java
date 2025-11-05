package com.fose.scout.before;

import java.util.List;

/**
 * BEFORE APPLYING THE SCOUT RULE
 *
 * This code works, but it has several small issues that make it
 * harder to understand and maintain:
 *
 * 1. Poor naming (what does "prcs" mean?)
 * 2. No documentation
 * 3. Raw types (List instead of List<Customer>)
 * 4. Old-style loops
 * 5. Dead/commented code
 * 6. Magic numbers without explanation
 *
 * These aren't major bugs, but they make the code harder to work with.
 * When you encounter code like this, apply the Scout Rule:
 * leave it better than you found it!
 */
public class CustomerProcessor {

    // TODO: This threshold was changed from 100 to 1000 last year
    // private static final int OLD_THRESHOLD = 100;
    private static final int THRESHOLD = 1000;

    public void prcs(List l) {
        for (int i = 0; i < l.size(); i++) {
            Object o = l.get(i);
            Customer c = (Customer) o;

            // Process only active customers
            if (c.isActive()) {
                double val = c.getTotalPurchases();

                // Check if customer is premium
                if (val > THRESHOLD) {
                    c.setPremium(true);
                    // Send email notification
                    // Email.send(c.getEmail(), "You are now premium!");
                }
            }
        }
    }

    public boolean chk(Customer c) {
        // Check if customer qualifies for discount
        if (c.getTotalPurchases() > 500 && c.isActive()) {
            return true;
        }
        return false;
    }

    // This method isn't used anymore but keeping it just in case
    public void oldMethod() {
        System.out.println("This does nothing");
    }
}
