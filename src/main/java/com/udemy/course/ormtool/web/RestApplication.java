package com.udemy.course.ormtool.web;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> result = new HashSet<>();

        result.add(PersonRestController.class);

        result.add(MsgConvertors.class);

        return result;
    }
}
