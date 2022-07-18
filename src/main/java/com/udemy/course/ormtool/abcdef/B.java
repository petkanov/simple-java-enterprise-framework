package com.udemy.course.ormtool.abcdef;

import com.udemy.course.ormtool.annotations.Bean;

@Bean
public class B implements BI {

    public B(D d) {
    }

    public String callMe() {
        return this.getClass().getSimpleName();
    }
}
