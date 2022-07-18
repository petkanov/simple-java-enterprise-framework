package com.udemy.course.ormtool.model;

public class AccountCard {
    private final Bank bank;
    private final Currency currency;
    private float money;

    public AccountCard(Bank bank, Currency currency) {
        this.bank = bank;
        this.currency = currency;
    }

    public Bank getBank() {
        return bank;
    }

    public double getAmountOfMoneyInAccount() {
        return money;
    }

    public void addMoneyIntoAccount(float amountOfMoney) {
        this.money += amountOfMoney;
    }

    public void removeMoneyFromAccount(float amountOfMoneyToRemove) {
        this.money -= amountOfMoneyToRemove;
    }

    public Currency getAccountCurrency() {
        return this.currency;
    }
}
