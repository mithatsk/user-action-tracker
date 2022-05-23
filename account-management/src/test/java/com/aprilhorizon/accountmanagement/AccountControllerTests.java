package com.aprilhorizon.accountmanagement;

import com.aprilhorizon.accountmanagement.models.AccountAmountRequest;
import com.aprilhorizon.accountmanagement.models.AccountRequest;
import com.aprilhorizon.accountmanagement.models.ChangeAccountNameRequest;
import com.aprilhorizon.accountmanagement.models.NewAccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AccountControllerTests {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        AccountService accountService = new AccountService();
        this.mockMvc = standaloneSetup(new AccountController(accountService)).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void testShouldCreateAccount() throws Exception {
        NewAccountRequest account = new NewAccountRequest();
        mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(account)))
                .andExpect(status().isOk());
    }

    @Test
    public void testShouldListAll() throws Exception {
        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Account account = asObject(result, Account.class);

        mockMvc.perform(get("/account/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber", Matchers.is(account.getAccountNumber())));
    }

    @Test
    public void testShouldAccountExist() throws Exception {
        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Account account = asObject(result, Account.class);

        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(get("/account")
                        .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName", Matchers.is(account.getAccountName())));
    }

    @Test
    public void testShouldIncreaseBalanceAfterDeposit() throws Exception {
        // Create an account and deposit some amount and check if the amount is greater than zero

        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Account account = asObject(result, Account.class);

        AccountAmountRequest accountAmountRequest = new AccountAmountRequest();
        accountAmountRequest.setAmount(50.0);
        accountAmountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountRequest)))
                .andExpect(status().isOk());


        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(get("/account")
                        .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", Matchers.greaterThan(0.0)));
    }

    @Test
    public void testShouldDecreaseBalanceAfterWithdraw() throws Exception {
        // Create an account and deposit some amount
        // and then withdraw some amount to check if the amount has changed

        //1 Create Account
        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Account account = asObject(result, Account.class);

        //2 - Deposit account
        AccountAmountRequest accountAmountRequest = new AccountAmountRequest();
        accountAmountRequest.setAmount(200.0);
        accountAmountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountRequest)))
                .andExpect(status().isOk());

        //3 - Check Balance
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        MvcResult resultAfterDeposit = mockMvc.perform(get("/account/balance")
                .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andReturn();

        Double balanceAfterDeposit = asObject(resultAfterDeposit, Double.class);

        //4 - Withdraw amount
        AccountAmountRequest accountAmountWithdrawReq = new AccountAmountRequest();
        accountAmountWithdrawReq.setAccountNumber(account.getAccountNumber());
        accountAmountWithdrawReq.setAmount(60.0);

        mockMvc.perform(put("/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountWithdrawReq)))
                .andExpect(status().isOk());


        //5 - Check Balance after withdraw

        MvcResult resultAfterWithdraw = mockMvc.perform(get("/account/balance")
                        .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andReturn();

        Double balanceAfterWithdraw = asObject(resultAfterWithdraw, Double.class);

        Assertions.assertNotEquals(balanceAfterDeposit, balanceAfterWithdraw);
    }

    @Test
    public void testShouldAmountNotChangeAfterWithdraw() throws Exception {
        // Create an account and withdraw some amount when balance is less than the amount withdrawn and check if balance did not change.
        // 1 - Create account

        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(asJsonString(newAccountRequest)))
                    .andExpect(status().isOk())
                    .andReturn();

        Account account = asObject(result, Account.class);

        // 2 - withdraw amount
        AccountAmountRequest accountAmountWithdrawReq = new AccountAmountRequest();
        accountAmountWithdrawReq.setAccountNumber(account.getAccountNumber());
        accountAmountWithdrawReq.setAmount(60.0);

        mockMvc.perform(put("/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountWithdrawReq)))
                .andExpect(status().isOk());

        // 3 - Check balance after withdraw
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        MvcResult resultAfterWithdraw = mockMvc.perform(get("/account/balance")
                .param("accountNumber", accountRequest.getAccountNumber()))
        .andExpect(status().isOk())
        .andReturn();

        Double balanceAfterWithdraw = asObject(resultAfterWithdraw, Double.class);

        Assertions.assertEquals(balanceAfterWithdraw, 0);
    }

    @Test
    public void testShouldNameChangeAfterChangeNameCalled() throws Exception {
        // Create an account and change the name then check if name is updated.

        //1 Create Account
        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Account account = asObject(result, Account.class);

        //2 change Name
        ChangeAccountNameRequest changeAccountNameRequest = new ChangeAccountNameRequest();
        changeAccountNameRequest.setNewName("ABC");
        changeAccountNameRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/changeName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(changeAccountNameRequest)))
                .andExpect(status().isOk());

        //3 Validate if name is changed
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(get("/account")
                        .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName", Matchers.not(account.getAccountName())));
    }

    @Test
    public void testShouldBalanceReturnResult() throws Exception {
        // Create an account and call balance endpoint to see if it returns a value
        //1 Create Account
        NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Account account = asObject(result, Account.class);

        //2 - Deposit account
        AccountAmountRequest accountAmountRequest = new AccountAmountRequest();
        accountAmountRequest.setAmount(200.0);
        accountAmountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountRequest)))
                .andExpect(status().isOk());

        //3 - Check Balance
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        MvcResult resultAfterDeposit = mockMvc.perform(get("/account/balance")
                .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andReturn();

        Double balanceAfterDeposit = asObject(resultAfterDeposit, Double.class);

        //4 - Withdraw amount
        AccountAmountRequest accountAmountWithdrawReq = new AccountAmountRequest();
        accountAmountWithdrawReq.setAccountNumber(account.getAccountNumber());
        accountAmountWithdrawReq.setAmount(60.0);

        mockMvc.perform(put("/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountWithdrawReq)))
                .andExpect(status().isOk());

        //5 - Check Balance after withdraw

        MvcResult resultAfterWithdraw = mockMvc.perform(get("/account/balance")
                        .param("accountNumber", accountRequest.getAccountNumber()))
                .andExpect(status().isOk())
                .andReturn();

        Double balanceAfterWithdraw = asObject(resultAfterWithdraw, Double.class);

        Assertions.assertNotEquals(balanceAfterDeposit, balanceAfterWithdraw);
    }

    private <T> T asObject(MvcResult result, Class<T> type) throws Exception {
        String contentString = result.getResponse().getContentAsString();
        return new ObjectMapper().readValue(contentString, type);
    }

    private String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
