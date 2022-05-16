package com.aprilhorizon.accountmanagement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AccountServiceTests {
    private AccountService accountService;

    @BeforeEach
    public void setup(){
        accountService = new AccountService();
    }

    @Test
    public void testCreateAccount() {
        accountService.createAccount("ABC", "123");
        accountService.createAccount("XYZ", "456");
        assertEquals(2, accountService.getAccounts().size());
    }

    @Test
    public void testInitialBalance() {
        accountService.createAccount("Saving Account", "561");
        assertEquals(0, accountService.getBalance("561"));
    }

    @Test
    public void testBalanceAfterDeposit() {
        accountService.createAccount("Saving Account", "123");
        accountService.deposit("123", 500.0);
        assertEquals(500, accountService.getBalance("123"));
    }

    @Test
    public void testInsufficientBalanceWithdraw() {
        accountService.createAccount("Saving Account", "123");
        accountService.withdraw("123", 50.0);
        assertEquals(0, accountService.getBalance("123"));
    }

    @Test
    public void testBalanceAfterWithdraw() {
        accountService.createAccount("Saving Account", "123");
        accountService.deposit("123", 80.0);
        accountService.withdraw("123", 50.0);
        assertEquals(30, accountService.getBalance("123"));
    }

    @Test
    public void testGetAccount() {
        accountService.createAccount("Saving Account", "123");
        assertEquals("Saving Account", accountService.getAccount("123").getAccountName());
        assertEquals(0.0, accountService.getAccount("123").getBalance());
    }

    @Test
    public void testGetAccountName() {
        accountService.createAccount("Saving Account", "123");
        assertEquals("Saving Account", accountService.getAccountName("123"));
    }

    @Test
    public void testChangeName() {
        accountService.createAccount("Saving Account", "123");
        accountService.changeName("123", "Modified Account");
        assertNotEquals("Saving Account", accountService.getAccountName("123"));
        assertEquals("Modified Account", accountService.getAccountName("123"));
    }

    @Test
    public void getAccountsIfNoAccountExists() {
        assertEquals(0, accountService.getAccounts().size());
    }
}
