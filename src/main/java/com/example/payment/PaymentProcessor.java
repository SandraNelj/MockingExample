package com.example.payment;

public class PaymentProcessor {
    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final EmailNotifier emailNotifier;
    private final String apiKey;

    public PaymentProcessor(
            PaymentGateway paymentGateway,
            PaymentRepository paymentRepository,
            EmailNotifier emailNotifier,
            String apiKey
    ) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
        this.emailNotifier = emailNotifier;
        this.apiKey = apiKey;
    }

    public boolean processPayment(double amount) {
        PaymentApiResponse response = paymentGateway.charge(apiKey, amount);

        if (response.success()) {
            paymentRepository.savePayment(amount, "SUCCESS");
            emailNotifier.sendPaymentConfirmation("user@example.com", amount);
        }
        return response.success();
    }
}
