package com.aprilhorizon.accountmanagement;

import com.aprilhorizon.accountmanagement.models.AccountAmountRequest;
import com.aprilhorizon.accountmanagement.models.ChangeAccountNameRequest;
import com.aprilhorizon.accountmanagement.models.NewAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/list")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> accountList() {
        return accountService.getAccounts();
    }

    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@RequestParam String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    @GetMapping("/account/balance")
    @ResponseStatus(HttpStatus.OK)
    public Double getBalance(@RequestParam String accountNumber) {
        return accountService.getBalance(accountNumber);
    }

    @GetMapping("/account/name")
    @ResponseStatus(HttpStatus.OK)
    public String getAccountName(@RequestParam String accountNumber) {
        return accountService.getAccountName(accountNumber);
    }

    @PostMapping("/account/new")
    @ResponseStatus(HttpStatus.OK)
    public Account newAccount(@RequestBody NewAccountRequest request) {
        String accountNumber = UUID.randomUUID().toString();
        return accountService.createAccount(request.getAccountName(), accountNumber);
    }

    @PutMapping("/account/changeName")
    @ResponseStatus(HttpStatus.OK)
    public void changeAccountName(@RequestBody ChangeAccountNameRequest request) {
        accountService.changeName(request.getAccountNumber(), request.getNewName());
    }

    @PutMapping("/account/withdraw")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw(@RequestBody AccountAmountRequest request) {
        accountService.withdraw(request.getAccountNumber(), request.getAmount());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/account/deposit")
    public void deposit(@RequestBody AccountAmountRequest request) {
        accountService.deposit(request.getAccountNumber(), request.getAmount());
    }
}
