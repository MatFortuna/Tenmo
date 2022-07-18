package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.util.BasicLogger;
import com.techelevator.util.BasicLoggerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class AccountService {

    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private Account account;
    private BasicLogger basicLogger;
    private BasicLoggerException basicLoggerException;

    public void getBalance (HttpEntity request){

        try {

        BigDecimal balance = restTemplate.exchange
                (API_BASE_URL + "account/balance", HttpMethod.GET, request, BigDecimal.class).getBody();
        System.out.println("You currently have: " + balance +  " TE Bucks.");
        } catch (
                RestClientResponseException rcre) {
            basicLogger.log("" + rcre.getRawStatusCode());
        } catch (
                ResourceAccessException rae) {
            basicLogger.log("" + rae.getLocalizedMessage());
    }
}
}
