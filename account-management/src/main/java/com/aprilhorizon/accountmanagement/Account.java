package com.aprilhorizon.accountmanagement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    private String accountNumber;
    private String accountName;
    private double balance;

    public Account() {
        this.accountNumber = "";
        this.accountName = "";
        this.balance = 0.0;
    }

    public Account(String accountName) {
        this.accountNumber = UUID.randomUUID().toString();;
        this.accountName = accountName;
        this.balance = 0;
    }

    public void withdraw(double amount) {
        if (this.balance >= amount)
            this.balance -= amount;
        else
            System.out.println("Insufficient amount");
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void changeName(String newName) {
        this.accountName = newName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() { return this.accountName; }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
}