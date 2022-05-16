package com.aprilhorizon.accountmanagement;

import com.aprilhorizon.accountmanagement.models.AccountAmountRequest;
import com.aprilhorizon.accountmanagement.models.AccountRequest;
import com.aprilhorizon.accountmanagement.models.ChangeAccountNameRequest;
import com.aprilhorizon.accountmanagement.models.NewAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class AccountController {

    private final AccountService accountService;

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
    public Account getAccount(@RequestBody AccountRequest request) {
        return accountService.getAccount(request.getAccountNumber());
    }

    @GetMapping("/account/balance")
    @ResponseStatus(HttpStatus.OK)
    public double getBalance(@RequestBody AccountRequest request) {
        return accountService.getBalance(request.getAccountNumber());
    }

    @GetMapping("/account/name")
    @ResponseStatus(HttpStatus.OK)
    public String getAccountName(@RequestBody AccountRequest request) {
        return accountService.getAccountName(request.getAccountNumber());
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
