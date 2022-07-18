package com.udemy.course.ormtool.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bank {
    private String name;
    private List<Person> clients = new ArrayList<>();
    private List<AccountCard> accounts = new ArrayList<>();

    public Bank(String name) {
        this.name = name;
    }

    public void createAccount(Person person, Currency currency) {
        AccountCard card = new AccountCard(this, currency);
        card.addMoneyIntoAccount( new Random().nextInt(1000));
        person.addBankAccountCard(card);
        clients.add(person);
    }

    public List<Person> getClients() {
        return clients;
    }
}
