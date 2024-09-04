package com.bankingapi.bankingapi.model;

public class TransactionDTO {
    private String accountSender;
    private String accountReceiver;
    private double funds;

    public String getAccountSender() {
        return accountSender;
    }

    public String getAccountReceiver() {
        return accountReceiver;
    }

    public double getFunds() {
        return funds;
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
}
