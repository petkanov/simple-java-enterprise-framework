package com.udemy.course.ormtool.abcdef;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.context.Environment;

@Bean
public class C {

    public C(AI ai, E bi, Environment environment) {
    }

    public String callMe() {
        return this.getClass().getSimpleName();
    }
}
