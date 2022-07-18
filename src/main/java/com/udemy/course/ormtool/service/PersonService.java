package com.udemy.course.ormtool.service;

public interface PersonService {
    String getData(String s);

    default void setName(String name) {
    }
}
