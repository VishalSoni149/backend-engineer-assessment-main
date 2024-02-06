package com.midas.app.providers.external.stripe;

import com.stripe.Stripe;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeApi {
  private final StripeConfiguration stripeConfiguration;

  public String createCustomer(String email) {
    Stripe.apiKey = stripeConfiguration.getApiKey();

    try {
      Customer customer = Customer.create(CustomerCreateParams.builder().setEmail(email).build());
      return customer.getId();
    } catch (Exception e) {
      // Handle Stripe API exception
      throw new RuntimeException("Failed to create customer in Stripe", e);
    }
  }
}
