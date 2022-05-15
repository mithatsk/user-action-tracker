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

    public Account createAccount(String accountName, String accountNumber) {
        Account account = new Account(accountName, accountNumber);
        accounts.add(account);
        System.out.println("createAccount triggered, for account number: " + accountNumber);
        return account;
    }

    public void withdraw(String accountNumber, Double amount) {
        System.out.println("withdrawn triggered, for account number: " + accountNumber + " withdraw amount: " + amount);
        getAccountFor(accountNumber).withdraw(amount);
    }

    public void deposit(String accountNumber, Double amount) {
        getAccountFor(accountNumber).deposit(amount);
        System.out.println("deposit triggered, for account number: " + accountNumber + ", deposited amount: " + amount);
    }

    public double getBalance(String accountNumber) {
        double balance = getAccountFor(accountNumber).getBalance();
        System.out.println("getBalance triggered, for account number: " + accountNumber + ", balance: " + balance);
        return balance;
    }

    public String getAccountName(String accountNumber) {
        String accountName = getAccountFor(accountNumber).getAccountName();
        System.out.println("getAccountName triggered, for accountNumber: " + accountNumber + ", accountName:" + accountNumber);
        return accountName;
    }

    public void changeName(String accountNumber, String newName) {
        getAccountFor(accountNumber).changeName(newName);
        System.out.println("changeName triggered for accountNumber: " + accountNumber + ", newName: " + newName);
    }

    public Account getAccount(String accountNumber) {
        Account account = getAccountFor(accountNumber);
        System.out.println("getAccount triggered, returned account: " + ASEUtil.asJsonString(account));
        return account;
    }

    private Account getAccountFor(String accountNumber) {
        Account account = accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst().get();
        return account;
    }

    public List<Account> getAccounts() {
        System.out.println("getAccounts triggered, accounts: " + ASEUtil.asJsonString(accounts));
        return accounts;
    }
}
