package com.example.maibank.models;

import java.util.List;

public class Transactions {
    private List<Transaction> transactionList;

    public Transactions(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public Transactions() {
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
