package com.aprilhorizon.accountmanagement;

import java.util.UUID;

public class Account {
    private String accountNumber;
    private String accountName;
    private double balance;

    public Account(String accountName) {
        this.accountNumber = UUID.randomUUID().toString();;
        this.accountName = accountName;
        this.balance = 0;
    }

    public void withdraw(double amount) {
        if (this.balance >= amount)
            this.balance -= amount;
        else
            System.out.println("Insufficient funds");
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return this.balance;
    }

    public void changeName(String newName) {
        this.accountName = newName;
    }

    public String getAccountNumber() {
        return this.accountNumber;
    }

    public String getAccountName() { return this.accountName; }
}