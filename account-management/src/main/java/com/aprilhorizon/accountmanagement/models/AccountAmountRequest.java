package com.aprilhorizon.accountmanagement.models;

public class AccountAmountRequest {
    private String accountNumber;
    private Double amount;

    public AccountAmountRequest() {
        this.amount = 0.0;
        this.accountNumber = "";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
