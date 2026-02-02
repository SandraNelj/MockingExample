package com.example.payment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentProcessorTest {

    @Mock
    PaymentGateway paymentGateway;
    @Mock
    PaymentRepository paymentRepository;
    @Mock
    EmailNotifier emailNotifier;

    PaymentProcessor paymentProcessor;

    @BeforeEach
    void setUp() {
        paymentProcessor = new PaymentProcessor(
                paymentGateway,
                paymentRepository,
                emailNotifier,
                "test-api-key"
        );
    }

    @Test
    @DisplayName("Successful payment triggers database save and notification")
    void processPayment_successful() {
        when (paymentGateway.charge("test-api-key", 100.0))
                .thenReturn(new PaymentApiResponse(true));

        boolean result = paymentProcessor.processPayment(100.0);

        assertThat(result).isTrue();
        verify(paymentRepository).savePayment(100.0, "SUCCESS");
        verify(emailNotifier).sendPaymentConfirmation("user@example.com", 100.0);
    }

    @Test
    @DisplayName("Failed payment does not save or send email")
    void processPayment_failed() {
        when (paymentGateway.charge("test-api-key", 100.0))
        .thenReturn(new PaymentApiResponse(false));

        boolean result = paymentProcessor.processPayment(100.0);

        assertThat(result).isFalse();
        verifyNoInteractions(paymentRepository, emailNotifier);
    }
}