package com.example.paymentservice.paymentgateway;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.param.PaymentLinkCreateParams;
import com.stripe.param.PriceCreateParams;
import org.springframework.stereotype.Component;

@Component
public class StripePaymentGateway implements IPaymentGateway {

    @Override
    public String generatePaymentLink(Long amount, String orderId, String phoneNumber, String name, String email) {
        Price price = createPrice(amount);
        try {
            PaymentLinkCreateParams params = PaymentLinkCreateParams.builder()
                    .addLineItem(
                            PaymentLinkCreateParams.LineItem.builder()
                                    .setPrice(price.getId())
                                    .setQuantity(1L)
                                    .build()
                    ).setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder() // extensive use of builder pattern
                            .setType(PaymentLinkCreateParams.AfterCompletion.Type.REDIRECT)
                            .setRedirect(PaymentLinkCreateParams.AfterCompletion.Redirect.builder()
                                    .setUrl("https://scaler.com").build())
                            .build())
                    .build();
            PaymentLink paymentLink = PaymentLink.create(params);
            return paymentLink.getUrl();
        } catch (StripeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    // For creating the Stripe payment link, we first need to create a price object
    private Price createPrice(Long amount) {
        try {
            PriceCreateParams params =
                    PriceCreateParams.builder()
                            .setCurrency("usd")
                            .setUnitAmount(amount)
                            .setRecurring(
                                    PriceCreateParams.Recurring.builder()
                                            .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                                            .build()
                            )
                            .setProductData(
                                    PriceCreateParams.ProductData.builder().setName("Gold Plan").build()
                            )
                            .build();
            Price price = Price.create(params);
            return price;
        }catch (StripeException exception) {
            throw new RuntimeException(exception.getMessage());
        }
    }
}
