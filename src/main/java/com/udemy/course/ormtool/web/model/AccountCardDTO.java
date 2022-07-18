package com.udemy.course.ormtool.web.model;

public class AccountCardDTO {
    private BankDTO bank;
    private double amountOfMoneyInAccount;

    public double getAmountOfMoneyInAccount() {
        return amountOfMoneyInAccount;
    }

    public void setAmountOfMoneyInAccount(double amountOfMoneyInAccount) {
        this.amountOfMoneyInAccount = amountOfMoneyInAccount;
    }

    public BankDTO getBank() {
        return bank;
    }

    public void setBank(BankDTO bank) {
        this.bank = bank;
    }
}
