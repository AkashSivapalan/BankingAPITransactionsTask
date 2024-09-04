package com.bankingapi.bankingapi.model;

public class Transaction {

    private String accountSender;
    private String accountReceiver;
    private double funds;

    private String receiverName;
    private String senderName;

    public void setAccountSender(String accountSender) {
        this.accountSender = accountSender;
    }

    public void setAccountReceiver(String accountReceiver) {
        this.accountReceiver = accountReceiver;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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

    public String getReceiverName() {
        return receiverName;
    }

    public String getSenderName() {
        return senderName;
    }
}
