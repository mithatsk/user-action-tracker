package com.aprilhorizon.accountmanagement;

import com.aprilhorizon.accountmanagement.models.AccountAmountRequest;
import com.aprilhorizon.accountmanagement.models.AccountRequest;
import com.aprilhorizon.accountmanagement.models.NewAccountRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    public void Should_Increase_Balance_After_Deposit() throws Exception {
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
        accountAmountRequest.setAmount(50);
        accountAmountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountRequest)))
                .andExpect(status().isOk());


        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(get("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance", Matchers.greaterThan(0.0)));
    }

    @Test
    public void Should_Decrease_Balance_After_Withdraw() throws Exception {
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
        accountAmountRequest.setAmount(200);
        accountAmountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountRequest)))
                .andExpect(status().isOk());

        //3 - Check Balance
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        MvcResult resultAfterDeposit = mockMvc.perform(get("/account/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        double balanceAfterDeposit = asObject(resultAfterDeposit, Double.class);

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        double balanceAfterWitdraw = asObject(resultAfterWithdraw, Double.class);

        Assertions.assertNotEquals(balanceAfterDeposit, balanceAfterWitdraw);
    }

    @Test
    public void Should_Amount_Not_Change_After_Withdraw() {
        // Create an account and withdraw some amount when balance is less than the amount withdrawn and check if balance did not change.

     
  1// Create account 

      NewAccountRequest newAccountRequest = new NewAccountRequest();
        newAccountRequest.setAccountName("first-account");
        MvcResult result = mockMvc.perform(post("/account/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(newAccountRequest)))
                .andExpect(status().isOk())
                .andReturn();

     Account account = asObject(result, Account.class);

    2// withdraw amount 
       AccountAmountRequest accountAmountWithdrawReq = new AccountAmountRequest();
        accountAmountWithdrawReq.setAccountNumber(account.getAccountNumber());
        accountAmountWithdrawReq.setAmount(60.0);

        mockMvc.perform(put("/account/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountWithdrawReq)))
                .andExpect(status().isOk());

   3// Check balance after withdraw

             MvcResult resultAfterWithdraw = mockMvc.perform(get("/account/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        double balanceAfterWitdraw = asObject(resultAfterWithdraw, Double.class);

        Assertions.assertNotEquals(balanceAfterDeposit, balanceAfterWitdraw);
    }

    @Test
    public void Should_Name_Change_After_Change_Name_Called() {
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

   //change Name 

         AccountchangeName accountchangeNameReq = new AccountNameRequest();
        accountchangeNameReq.setNewname(account.getAccountName());
        accountNewNameReq.setName(0.0);

        mockMvc.perform(put("/account/changeName")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountchangeNameReq)))
                .andExpect(status().isOk());

//check new name on the list

    }

    @Test
    public void Should_Balance_Return_Result() {
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
        accountAmountRequest.setAmount(200);
        accountAmountRequest.setAccountNumber(account.getAccountNumber());

        mockMvc.perform(put("/account/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountAmountRequest)))
                .andExpect(status().isOk());

        //3 - Check Balance
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setAccountNumber(account.getAccountNumber());

        MvcResult resultAfterDeposit = mockMvc.perform(get("/account/balance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        double balanceAfterDeposit = asObject(resultAfterDeposit, Double.class);

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
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(accountRequest)))
                .andExpect(status().isOk())
                .andReturn();

        double balanceAfterWitdraw = asObject(resultAfterWithdraw, Double.class);

        Assertions.assertNotEquals(balanceAfterDeposit, balanceAfterWitdraw);

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
