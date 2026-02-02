package com.example.payment;

public interface EmailNotifier {
    void sendPaymentConfirmation(String email, double amount);
}
