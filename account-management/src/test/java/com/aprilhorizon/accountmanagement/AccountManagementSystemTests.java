package com.aprilhorizon.accountmanagement;


import com.aprilhorizon.accountmanagement.models.AccountRequest;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountManagementSystemTests {

    @Test
    @Disabled
    public void testErrorHandlingReturnsNotFound() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/gedfsgdfgdfgfdgdfgdf";

        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
        }
    }

    @Test
    @Disabled
    public void testErrorHandlingReturnsBadRequest() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/account";

        try {
            restTemplate.getForEntity(url, String.class);
        } catch (HttpClientErrorException e) {
            assertEquals(HttpStatus.BAD_REQUEST, e.getStatusCode());
        }
    }

    @Test
    @Disabled
    public void testCreateNewAccount() {
        RestTemplate restTemplate = new RestTemplate();
        String newAccountPostUrl = "http://localhost:8080/account/new";
        Account account = new Account("XYZ", "123");
        ResponseEntity<Account> entity = restTemplate.postForEntity(newAccountPostUrl, account, Account.class);
        assertEquals(HttpStatus.CREATED, entity.getStatusCode());
    }
}
