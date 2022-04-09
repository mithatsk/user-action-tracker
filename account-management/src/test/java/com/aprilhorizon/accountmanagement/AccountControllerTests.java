package com.aprilhorizon.accountmanagement;

import com.aprilhorizon.accountmanagement.models.AccountRequest;
import com.aprilhorizon.accountmanagement.models.NewAccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class AccountControllerTests {

    private AccountService accountService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.accountService = new AccountService();
        this.mockMvc = standaloneSetup(new AccountController(this.accountService)).alwaysExpect(status().isOk()).build();
    }

    @Test
    public void Should_Create_Account() throws Exception {
        NewAccountRequest account = new NewAccountRequest();
        mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(account)))
                        .andExpect(status().isOk());
    }

    @Test
    public void Should_List_All() throws Exception {
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
    public void Should_Account_Exist() throws Exception {
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
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountName", Matchers.is(account.getAccountName())));
    }

    @Test
    public void Should_Increase_Balance_After_Deposit() {
        // Create an account and deposit some amount and check if the amount is greater than zero
    }

    @Test
    public void Should_Decrease_Balance_After_Withdraw() {
        // Create an account and deposit some amount and then withdraw some amount to check if the amount has changed
    }

    @Test
    public void Should_Amount_Not_Change_After_Withdraw() {
        // Create an account and withdraw some amount when balance is less than the amount withdrawn and check if balance did not change.
    }

    @Test
    public void Should_Name_Change_After_Change_Name_Called() {
        // Create an account and change the name then check if name is updated.
    }

    @Test
    public void Should_Balance_Return_Result() {
        // Create an account and call balance endpoint to see if it returns a value
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
