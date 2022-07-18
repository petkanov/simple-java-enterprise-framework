package com.udemy.course.ormtool.service;

import com.udemy.course.ormtool.annotations.Entity;
import com.udemy.course.ormtool.annotations.Transactional;

import java.nio.file.Paths;

@Entity
public class PersonParamImpl implements PersonService {
    private String name = "John";
    private int age = 21;

    public PersonParamImpl(String name, Integer age){
        this.name = name;
        this.age = age;
    }
    @Transactional
    public String getData(String s) {
        return s + name + age + "; " + Paths.get("").toAbsolutePath();
//        return s + name + age + "; " + System.getProperty("user.dir");
    }

    public void setName(String name) {
        this.name = name;
    }
}
