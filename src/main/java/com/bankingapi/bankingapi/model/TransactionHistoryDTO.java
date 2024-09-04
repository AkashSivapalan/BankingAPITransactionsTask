package com.bankingapi.bankingapi.model;

import java.time.LocalDateTime;


//DTO for return object
public class TransactionHistoryDTO {

    private double postBalance;
    private String accountSender;
    private String accountReceiver;
    private double funds;

    private LocalDateTime timestamp;

    public double getPostBalance() {
        return postBalance;
    }

    public String getAccountSender() {
        return accountSender;
    }

    public String getAccountReceiver() {
        return accountReceiver;
    }

    public double getFunds() {
        return funds;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setPostBalance(double postBalance) {
        this.postBalance = postBalance;
    }

    public void setAccountSender(String accountSender) {
        this.accountSender = accountSender;
    }

    public void setAccountReceiver(String accountReceiver) {
        this.accountReceiver = accountReceiver;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
