package com.udemy.course.ormtool.abcdef;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.context.Environment;

@Bean
public class A implements AI {
    public A(Environment environment, E e) {
    }

    public String callMe() {
        return this.getClass().getSimpleName();
    }
}
