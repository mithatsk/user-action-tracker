package com.aprilhorizon.accountmanagement;

import com.aprilhorizon.accountmanagement.models.NewAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/list")
    public List<Account> accountList() {
        return accountService.accounts;
    }

    @PostMapping("/account/new")
    @ResponseStatus(HttpStatus.CREATED)
    public String newAccount(@RequestBody NewAccountRequest request) {
        System.out.println(request.getAccountName());
        accountService.createAccount(request.getAccountName());
        return "success";
    }

    @PostMapping("/account/deposit")
    public String deposit(@RequestBody double amount, @RequestBody String accountNumber) {
        accountService.deposit(accountNumber, amount);
        return "success";
    }
}
