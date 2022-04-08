package com.aprilhorizon.accountmanagement.models;

public class AccountRequest {
    private String accountNumber;

    public AccountRequest() {
        this.accountNumber = "";
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
