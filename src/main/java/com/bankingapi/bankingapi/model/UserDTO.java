package com.bankingapi.bankingapi.model;

public class UserDTO {

    private String name;
    private double balance;

    private String accountId;

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
