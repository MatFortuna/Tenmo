package com.techelevator.tenmo.model;

public class TenmoUser {

    private Long userId;
    private String userName;
    private String passwordHash;

    public TenmoUser(Long userId, String userName, String passwordHash) {
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
