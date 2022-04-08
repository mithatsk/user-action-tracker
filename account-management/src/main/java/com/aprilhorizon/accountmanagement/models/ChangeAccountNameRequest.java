package com.aprilhorizon.accountmanagement.models;

public class ChangeAccountNameRequest {
    private String accountNumber;
    private String newName;

    public ChangeAccountNameRequest() {
        this.accountNumber = "";
        this.newName = "";
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getNewName() {
        return this.newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }
}
