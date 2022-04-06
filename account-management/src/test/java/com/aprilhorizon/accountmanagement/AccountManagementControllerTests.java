package com.aprilhorizon.accountmanagement;


import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(AccountController.class)
public class AccountManagementControllerTests {

    @MockBean
    AccountService accountService;
    @Autowired
    MockMvc mockMvc;
    final double ZERO_VALUE = 0.0;

    @Test
    public void testfindAll() throws Exception {
        Account account = new Account("ABC");
        List<Account> accounts = Arrays.asList(account);

        Mockito.when(accountService.getAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/account/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].accountName", Matchers.is("ABC")));
    }

    @Test
    public void testInitialBalanceIsZero() throws Exception {
        Account account = new Account("AnyName");
        List<Account> accounts = Arrays.asList(account);

        Mockito.when(accountService.getAccounts()).thenReturn(accounts);

        mockMvc.perform(get("/account/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].balance", Matchers.equalTo(ZERO_VALUE)));

    }
}
