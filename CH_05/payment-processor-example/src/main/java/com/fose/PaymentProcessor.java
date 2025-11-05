package com.fose;

/**
 * PaymentProcessor handles various payment processing operations.
 * This class demonstrates how well-named tests serve as documentation
 * for understanding business rules and supported features.
 */
public class PaymentProcessor {

    /**
     * Validates a credit card number using the Luhn algorithm (simplified version)
     */
    public boolean validateCreditCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isBlank()) {
            return false;
        }

        // Remove spaces and dashes
        String cleanedNumber = cardNumber.replaceAll("[\\s-]", "");

        // Must be numeric and between 13-19 digits
        if (!cleanedNumber.matches("\\d{13,19}")) {
            return false;
        }

        // Simplified Luhn check
        return passesLuhnCheck(cleanedNumber);
    }

    /**
     * Processes a credit card transaction
     */
    public PaymentResult processCreditCardTransaction(String cardNumber, double amount) {
        if (!validateCreditCardNumber(cardNumber)) {
            return new PaymentResult(false, "Invalid credit card number");
        }

        if (amount <= 0) {
            return new PaymentResult(false, "Amount must be greater than zero");
        }

        // Simulate successful payment processing
        return new PaymentResult(true, "Payment of $" + amount + " processed successfully");
    }

    /**
     * Processes Orange Pay payment
     * NOTE: Orange Pay is NOT currently supported by our system
     */
    public PaymentResult processOrangePay(String accountId, double amount) {
        // Business rule: Orange Pay is not supported
        throw new UnsupportedPaymentMethodException("Orange Pay is not currently supported");
    }

    /**
     * Determines if a credit card type is supported
     */
    public boolean isCreditCardTypeSupported(String cardType) {
        return switch (cardType.toUpperCase()) {
            case "VISA", "MASTERCARD", "AMEX", "DISCOVER" -> true;
            case "ORANGEPAY" -> false; // Business rule revealed through tests
            default -> false;
        };
    }

    /**
     * Simplified Luhn algorithm check
     */
    private boolean passesLuhnCheck(String number) {
        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {
            int digit = Character.getNumericValue(number.charAt(i));

            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            alternate = !alternate;
        }

        return sum % 10 == 0;
    }
}
