package com.fose.scout.before;

/**
 * Customer domain model used by the "before" example.
 */
public class Customer {
    private String name;
    private String email;
    private double totalPurchases;
    private boolean active;
    private boolean premium;

    public Customer(String name, String email, double totalPurchases, boolean active) {
        this.name = name;
        this.email = email;
        this.totalPurchases = totalPurchases;
        this.active = active;
        this.premium = false;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getTotalPurchases() {
        return totalPurchases;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
