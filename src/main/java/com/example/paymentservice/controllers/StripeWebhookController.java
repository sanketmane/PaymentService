package com.example.paymentservice.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stripeWebhook")
public class StripeWebhookController {

    // stripe pg will call this api on receiving payment
    // In our case, we will configure stripe to trigger this
    // api on payment link generation or checkout(these options
    // are configurable on strip page.
    @PostMapping
    public void listenToEvents(@RequestBody String event) {
        System.out.println(event);
    }
}
