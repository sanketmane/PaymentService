package com.example.paymentservice.paymentgateway;

import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements IPaymentGateway {

    @Override
    public String generatePaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) {
        return null;
    }
}
