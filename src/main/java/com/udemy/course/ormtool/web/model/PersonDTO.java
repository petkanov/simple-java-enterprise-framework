package com.udemy.course.ormtool.web.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class PersonDTO {
    private int id;
    private String name;
    private int age;
    private List<AccountCardDTO> debitCards;
//    @JsonIgnore
    private List<PersonDTO> friends;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<AccountCardDTO> getDebitCards() {
        return debitCards;
    }

    public void setDebitCards(List<AccountCardDTO> debitCards) {
        this.debitCards = debitCards;
    }

    public List<PersonDTO> getFriends() {
        return friends;
    }

    public void setFriends(List<PersonDTO> friends) {
        this.friends = friends;
    }

}
