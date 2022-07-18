package com.udemy.course.ormtool.web.model;

import java.util.ArrayList;
import java.util.List;

public class BankDTO {
    private List<PersonDTO> accountHolders = new ArrayList<>();

    public List<PersonDTO> getAccountHolders() {
        return accountHolders;
    }

    public void setAccountHolders(List<PersonDTO> accountHolders) {
        this.accountHolders = accountHolders;
    }
}
