package com.udemy.course.ormtool.abcdef;

import com.udemy.course.ormtool.annotations.Bean;

@Bean
public class D implements DI {

    public  D(){
    }

    public String callMe() {
        return this.getClass().getSimpleName();
    }
}
