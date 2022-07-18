package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import com.techelevator.util.BasicLoggerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class TransferService {
    private Transfer transfer = new Transfer();
    private final RestTemplate restTemplate = new RestTemplate();
    private AuthenticatedUser currentUser;
    private static final String API_BASE_URL = "http://localhost:8080/";
    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private Account account;
    private BasicLogger basicLogger;
    private BasicLoggerException basicLoggerException;

    public void listUsers(HttpEntity entity) {

        try {
            Account[] accountList = null;
            ResponseEntity<Account[]> response = restTemplate.exchange(API_BASE_URL + "account", HttpMethod.GET, entity, Account[].class);
            accountList = response.getBody();
            for (int i = 0; i < accountList.length; i++) {
                Account currentAccount = accountList[i];
                System.out.println("Username: " + currentAccount.getUsername() + " || Account ID: " + currentAccount.getAccountId());
            }
        } catch (
                RestClientResponseException rcre) {
            System.out.println(rcre.getRawStatusCode() + rcre.getStatusText());
            basicLogger.log("" + rcre.getRawStatusCode());
        } catch (
                ResourceAccessException rae) {
            System.out.println("na na na");
            ;
        }
    }

    public void transferService(HttpEntity<Transfer> entity) {

        try {
            Boolean success = restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.POST, entity, Boolean.class).getBody();
            if (success) {
                System.out.println("Transfer successfully sent!");
            } else {
                System.out.println("Transfer could not be completed.");
            }

        } catch (RestClientResponseException rcre) {
            System.out.println(rcre.getRawStatusCode() + rcre.getStatusText());
            basicLogger.log("" + rcre.getRawStatusCode());
        } catch (ResourceAccessException rae) {
            System.out.println("na na na");
            ;
        }

    }

    public void getTransfers(HttpEntity entity) {

        try {
            Transfer[] transfer = null;
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfer/log", HttpMethod.GET, entity, Transfer[].class);
            transfer = response.getBody();
            for (int i = 0; i < transfer.length; i++) {
                Transfer currentTransfer = transfer[i];
                System.out.println("Transfer ID: " + currentTransfer.getTransferId() + " Account From: " + currentTransfer.getAccountFrom()
                        + " || Account To: " + currentTransfer.getAccountTo() + " || Amount " + currentTransfer.getAmount());
            }
        } catch (
                RestClientResponseException rcre) {
            basicLogger.log("" + rcre.getRawStatusCode());
        } catch (
                ResourceAccessException rae) {
            basicLogger.log("" + rae.getLocalizedMessage());
        }
    }

    public void getOneTransfer(HttpEntity entity, Long transferId) {

        try {
            Transfer[] transfer = null;
            ResponseEntity<Transfer[]> response = restTemplate.exchange(API_BASE_URL + "transfer/log", HttpMethod.GET, entity, Transfer[].class);
            transfer = response.getBody();
            boolean loop = false;
            for (int i = 0; i < transfer.length; i++) {
                Transfer transfer1 = transfer[i];
                if (transfer1.getTransferId().equals(transferId)) {
                    System.out.println("TransTransfer ID: " + transfer1.getTransferId() + " Account From: " + transfer1.getAccountFrom()
                            + " || Account To: " + transfer1.getAccountTo() + " || Amount: " + transfer1.getAmount() + " || Transfer Type ID: " + transfer1.getTransferTypeId() + " || Transfer Status ID: " + transfer1.getTransferStatusId());
                    loop = true;
                }
                }
            if (loop == false) {
                System.out.println("+++++++++++");
                    System.out.println("No ID exists.");
                }

        } catch (
                RestClientResponseException rcre) {
            basicLogger.log("" + rcre.getRawStatusCode());
        } catch (
                ResourceAccessException rae) {
            basicLogger.log("" + rae.getLocalizedMessage());
        }
    }
}




