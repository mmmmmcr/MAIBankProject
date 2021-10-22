package com.example.maibank.models;

import java.util.Map;

public class Accounts {
    Map<String, Account> accounts;

    public Accounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }

    public Accounts() {}

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Map<String, Account> accounts) {
        this.accounts = accounts;
    }
}
