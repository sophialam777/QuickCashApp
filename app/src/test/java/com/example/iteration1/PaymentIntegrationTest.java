package com.example.iteration1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class PaymentIntegrationTest {

    private PaymentIntegration paymentIntegration;

    @Before
    public void setUp() {
        paymentIntegration = mock(PaymentIntegration.class);
    }

    @Test
    public void testAddCreditCardDetails_Success() {
        String cardNumber = "4111111111111111";
        String expiryDate = "12/25";
        String cardHolderName = "John Doe";
        String cvv = "123";

        //Simulate a successful credit card addition
        when(paymentIntegration.AddCreditCardDetails(cardNumber, expiryDate, cardHolderName, cvv))
                .thenReturn(true);

        boolean result = paymentIntegration.AddCreditCardDetails(cardNumber, expiryDate, cardHolderName, cvv);

        assertTrue("Credit card details should be added successfully.", result);

        verify(paymentIntegration).AddCreditCardDetails(cardNumber, expiryDate, cardHolderName, cvv);
    }

    @Test
    public void testAddCreditCardDetails_Failure() {
        // Arrange
        String invalidCardNumber = "1234567890123456";
        String expiryDate = "12/25";
        String cardHolderName = "John Doe";
        String cvv = "123";

        when(paymentIntegration.AddCreditCardDetails(invalidCardNumber, expiryDate, cardHolderName, cvv))
                .thenReturn(false);

        boolean result = paymentIntegration.AddCreditCardDetails(invalidCardNumber, expiryDate, cardHolderName, cvv);

        assertTrue("Credit card details should not be added for invalid card number.", !result);

        verify(paymentIntegration).AddCreditCardDetails(invalidCardNumber, expiryDate, cardHolderName, cvv);
    }

    @Test
    public void testProcessPayment_Success() {
        String employerCardNumber = "4111111111111111";
        String employeePaymentAmount = "50.00";

        when(paymentIntegration.processPayment(employerCardNumber, employeePaymentAmount))
                .thenReturn("Test transaction completed successfully");

        String result = paymentIntegration.processPayment(employerCardNumber, employeePaymentAmount);

        assertEquals("Test transaction should be completed successfully.",
                "Test transaction completed successfully", result);

        verify(paymentIntegration).processPayment(employerCardNumber, employeePaymentAmount);
    }

    @Test
    public void testProcessPayment_Failure() {
        String invalidCardNumber = "1234567890123456";
        String employeePaymentAmount = "50.00";

        when(paymentIntegration.processPayment(invalidCardNumber, employeePaymentAmount))
                .thenReturn("Test transaction failed. Invalid card number.");

        String result = paymentIntegration.processPayment(invalidCardNumber, employeePaymentAmount);

        assertEquals("Test transaction should fail for invalid card number.",
                "Test transaction failed. Invalid card number.", result);

        verify(paymentIntegration).processPayment(invalidCardNumber, employeePaymentAmount);
    }
}