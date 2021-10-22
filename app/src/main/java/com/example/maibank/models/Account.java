package com.example.maibank.models;

public class Account {
    private double sold;
    private String iban;
    private String type;

    public Account(){}

    public Account(double sold, String iban, String type) {
        this.sold = sold;
        this.iban = iban;
        this.type = type;
    }

    public double getSold() {
        return sold;
    }

    public void setSold(double sold) {
        this.sold = sold;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
