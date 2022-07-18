package com.udemy.course.ormtool.service;

import com.udemy.course.ormtool.annotations.Entity;
import com.udemy.course.ormtool.annotations.Transactional;
@Entity
public class PersonNoArgsImpl implements PersonService {
    @Transactional
    @Override
    public String getData(String s) {
        return "No Arg Person " + s;
    }
}
