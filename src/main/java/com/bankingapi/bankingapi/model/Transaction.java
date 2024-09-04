package com.bankingapi.bankingapi.model;

import java.time.LocalDateTime;

public class Transaction {

    private String accountSender;
    private String accountReceiver;
    private double funds;

    private LocalDateTime timestamp;

    private double receiverBalance;
    private double senderBalance;

    public Transaction() {
        this.timestamp = LocalDateTime.now();
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

    public String getAccountSender() {
        return accountSender;
    }

    public String getAccountReceiver() {
        return accountReceiver;
    }

    public double getFunds() {
        return funds;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public double getReceiverBalance() {
        return receiverBalance;
    }

    public double getSenderBalance() {
        return senderBalance;
    }

    public void setReceiverBalance(double receiverBalance) {
        this.receiverBalance = receiverBalance;
    }

    public void setSenderBalance(double senderBalance) {
        this.senderBalance = senderBalance;
    }
}
