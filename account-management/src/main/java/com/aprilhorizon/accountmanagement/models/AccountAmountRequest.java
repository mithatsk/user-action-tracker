package com.aprilhorizon.accountmanagement.models;

public class AccountAmountRequest {
    private String accountNumber;
    private double amount;

    public AccountAmountRequest() {
        this.amount = 0;
        this.accountNumber = "";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
