package com.pet.common.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8050/api/v1/payment", name = "Payment")
public interface PaymentClient {

    @PostMapping
    long createPayment(@RequestBody CreatePaymentRequest request);
}
