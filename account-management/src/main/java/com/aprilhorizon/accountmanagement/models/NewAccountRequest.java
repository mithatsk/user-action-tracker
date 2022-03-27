package com.aprilhorizon.accountmanagement.models;

public class NewAccountRequest {
    private String accountName;

    public NewAccountRequest(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountName() {
        return this.accountName;
    }
}
