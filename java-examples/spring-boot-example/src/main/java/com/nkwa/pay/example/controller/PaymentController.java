package com.nkwa.pay.example.controller;

import io.github.nkwa.pay_sdk.Pay;
import io.github.nkwa.pay_sdk.models.errors.HttpError;
import io.github.nkwa.pay_sdk.models.operations.PostCollectResponse;
import io.github.nkwa.pay_sdk.models.operations.PostDisburseResponse;
import io.github.nkwa.pay_sdk.models.operations.GetPaymentsIdResponse;
import io.github.nkwa.pay_sdk.models.components.PaymentRequest;
import com.nkwa.pay.example.model.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentController {
    private final Pay pay;
    private final ObjectMapper objectMapper;

    public PaymentController(@Value("${PAY_API_KEY_AUTH}") String apiKey) {
        this.pay = Pay.builder()
            .apiKeyAuth(apiKey)
            .build();
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping(value = "/collect-payment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Object>>> collectPayment(@RequestBody com.nkwa.pay.example.model.PaymentRequest webRequest) {
        try {
            PaymentRequest sdkRequest = PaymentRequest.builder()
                .amount((long)webRequest.getAmount())
                .phoneNumber(webRequest.getPhoneNumber())
                .build();
            
            PostCollectResponse response = pay.payments().collect()
                .request(sdkRequest)
                .call();
            
            if (response.payment().isPresent()) {
                Map<String, Object> paymentData = objectMapper.convertValue(response.payment().get(), Map.class);
                return ResponseEntity.ok(new ApiResponse<>(paymentData));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(Map.of("status", "PENDING", "message", "Payment request submitted")));
            }
        } catch (HttpError e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ApiResponse<>(e.getMessage()));
        }
    }

    @PostMapping(value = "/disburse-payment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<Map<String, Object>>> disbursePayment(@RequestBody com.nkwa.pay.example.model.PaymentRequest webRequest) {
        try {
            PaymentRequest sdkRequest = PaymentRequest.builder()
                .amount((long)webRequest.getAmount())
                .phoneNumber(webRequest.getPhoneNumber())
                .build();
            
            PostDisburseResponse response = pay.payments().disburse()
                .request(sdkRequest)
                .call();
            
            if (response.payment().isPresent()) {
                Map<String, Object> paymentData = objectMapper.convertValue(response.payment().get(), Map.class);
                return ResponseEntity.ok(new ApiResponse<>(paymentData));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(Map.of("status", "PENDING", "message", "Payment request submitted")));
            }
        } catch (HttpError e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(e.getMessage()));
        }
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getPaymentStatus(@PathVariable String paymentId) {
        try {
            GetPaymentsIdResponse response = pay.payments().get(paymentId);
            
            if (response.payment().isPresent()) {
                Map<String, Object> paymentData = objectMapper.convertValue(response.payment().get(), Map.class);
                return ResponseEntity.ok(new ApiResponse<>(paymentData));
            } else {
                return ResponseEntity.ok(new ApiResponse<>(Map.of("id", paymentId, "status", "NOT_FOUND")));
            }
        } catch (HttpError e) {
            return ResponseEntity.badRequest().body(new ApiResponse<>(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ApiResponse<>(e.getMessage()));
        }
    }
}