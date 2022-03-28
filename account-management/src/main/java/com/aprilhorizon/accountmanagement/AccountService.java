package com.aprilhorizon.accountmanagement;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class AccountService {

    public List<Account> accounts;

    public AccountService(Account[] accounts) {
        this.accounts = Arrays.asList(accounts);
    }

    public AccountService() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(String accountName) {
        accounts.add(new Account(accountName));
    }

    public void withdraw(String accountNumber, double amount) {
        getAccount(accountNumber).withdraw(amount);
    }

    public void deposit(String accountNumber, double amount) {
        getAccount(accountNumber).deposit(amount);
    }

    public double getBalance(String accountNumber) {
        return getAccount(accountNumber).getBalance();
    }

    public String getAccountName(String accountNumber) {
        return getAccount(accountNumber).getAccountName();
    }

    public void changeName(String accountNumber, String newName) {
        getAccount(accountNumber).changeName(newName);
    }

    public Account getAccount(String accountNumber) {
        return accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst().get();
    }
}
