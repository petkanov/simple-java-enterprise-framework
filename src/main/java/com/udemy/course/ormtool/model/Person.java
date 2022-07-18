package com.udemy.course.ormtool.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public class Person {
    private Integer id = new Random().nextInt(100);
    private String name;
    private int age;
    private List<AccountCard> bankAccounts;
    private List<Person> friends;

    public Person(){}

    public Person(String name, int age){
        this.name = name;
        this.age = age;
        this.bankAccounts = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void addBankAccountCard(AccountCard card) {
        this.bankAccounts.add(card);
    }

    public List<AccountCard> getBankAccounts() {
        return bankAccounts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public List<Person> getFriends() {
        return Collections.unmodifiableList(friends);
    }

    public void setFriends(List<Person> friends) {
        this.friends.clear();
        this.friends.addAll(friends);
    }

    public void addFriend(Person person) {
        this.friends.add(person);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name +
                ", debitCards=" + (bankAccounts != null ? bankAccounts.size() : 0) +
                ", friends=" + (friends != null ? friends.size() : 0) +
                '}';
    }
}
