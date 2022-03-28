package com.aprilhorizon.accountmanagement.models;

public class NewAccountRequest {
    private String accountName;

    public NewAccountRequest() {
        this.accountName = "";
    }

    public String getAccountName() {
        return this.accountName;
    }
}
