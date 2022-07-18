package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private Long accountId;
    private Long userId;
    private BigDecimal balance;
    private String username;

    public Account(Long accountId, Long userId, BigDecimal balance) {
        this.accountId = accountId;
        this.userId = userId;
        this.balance = balance;
    }

    public Account() {
    }

    public Account(Long accountId, String username) {
        this.accountId = accountId;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserID(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                '}';
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
