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
        System.out.println("account created, account number:" + accountNumber);
        return account;
    }

    public void withdraw(String accountNumber, double amount) {
        getAccount(accountNumber).withdraw(amount);
        System.out.println("withdrawn, account number:" + accountNumber);
    }

    public void deposit(String accountNumber, double amount) {
        getAccount(accountNumber).deposit(amount);
        System.out.println("deposited, account number:" + accountNumber);
    }

    public double getBalance(String accountNumber) {
        double balance = getAccount(accountNumber).getBalance();
        System.out.println("balance, account number:" + accountNumber + "balance" + balance);
        return balance;
    }

    public String getAccountName(String accountNumber) {
        String accountName = getAccount(accountNumber).getAccountName();
        System.out.println("accountNumber" + accountNumber + "accountName:" + accountNumber);
        return accountName;
    }

    public void changeName(String accountNumber, String newName) {
        getAccount(accountNumber).changeName(newName);
        System.out.println("name changed to" + newName);
    }

    public Account getAccount(String accountNumber) {
        Account account = accounts.stream().filter(a -> a.getAccountNumber().equals(accountNumber)).findFirst().get();
        System.out.println("account:" + account);
        return account;
    }

    public List<Account> getAccounts() {
        System.out.println("accounts:" + accounts);
        return accounts;
    }
}
