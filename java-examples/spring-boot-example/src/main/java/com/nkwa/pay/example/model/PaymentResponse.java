package com.nkwa.pay.example.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentResponse {
    private String id;
    private long amount;
    private String currency;
    private String description;
    private long fee;
    private String merchantId;
    private String paymentType;
    private String phoneNumber;
    private String status;
    private String telecomOperator;
    private String createdAt;
    private String updatedAt;

    public static PaymentResponse fromMap(Object payment) {
        if (payment == null) {
            return null;
        }
        PaymentResponse response = new PaymentResponse();
        // The SDK returns the data as a map, so we'll set the fields directly
        java.util.Map<?, ?> paymentMap = (java.util.Map<?, ?>)payment;
        
        // Safely extract values with null checks
        response.id = (String) paymentMap.get("id");
        
        // Handle amounts and fees as numbers or nulls safely
        Number amountNumber = (Number) paymentMap.get("amount");
        response.amount = amountNumber != null ? amountNumber.longValue() : 0L;
        
        response.currency = (String) paymentMap.get("currency");
        response.description = (String) paymentMap.get("description");
        
        Number feeNumber = (Number) paymentMap.get("fee");
        response.fee = feeNumber != null ? feeNumber.longValue() : 0L;
        
        response.merchantId = (String) paymentMap.get("merchantId");
        response.paymentType = (String) paymentMap.get("paymentType");
        response.phoneNumber = (String) paymentMap.get("phoneNumber");
        response.status = (String) paymentMap.get("status");
        response.telecomOperator = (String) paymentMap.get("telecomOperator");
        response.createdAt = (String) paymentMap.get("createdAt");
        response.updatedAt = (String) paymentMap.get("updatedAt");
        return response;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public long getAmount() { return amount; }
    public void setAmount(long amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public long getFee() { return fee; }
    public void setFee(long fee) { this.fee = fee; }

    public String getMerchantId() { return merchantId; }
    public void setMerchantId(String merchantId) { this.merchantId = merchantId; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getTelecomOperator() { return telecomOperator; }
    public void setTelecomOperator(String telecomOperator) { this.telecomOperator = telecomOperator; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(String updatedAt) { this.updatedAt = updatedAt; }
}