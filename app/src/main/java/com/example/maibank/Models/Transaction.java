package com.example.maibank.Models;

public class Transaction {
    private String myAccount;
    private String destinationAccount;
    private String amount;
    private String transactionDate;

    public Transaction(String myAccount, String destinationAccount, String amount, String transactionDate) {
        this.myAccount = myAccount;
        this.destinationAccount = destinationAccount;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction() {
    }

    public String getMyAccount() {
        return myAccount;
    }

    public void setMyAccount(String myAccount) {
        this.myAccount = myAccount;
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
}
