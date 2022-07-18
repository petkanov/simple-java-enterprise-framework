package com.udemy.course.ormtool.context.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

public class CallbackStrategyResolver {
    private Logger log = LoggerFactory.getLogger(this.getClass());


//        this.isEntity = original.getClass().getAnnotation(Entity.class) != null;



    void preMethodCallback(Object object, Method method) {
        log.trace("Method: {}, Object: {}", method, object);

//        boolean isMethodTransactional = method.getAnnotation(Transactional.class) != null;;
//
//        if(isMethodTransactional){
//            log.debug("Database Transaction Started for <{}::{}>",original.getClass().getSimpleName(), method.getName());
//        }

    }

    void afterMethodCallback(Object object, Method method){
        log.trace("Method: {}, Object: {}", method, object);

//        if (isEligibleForDatabaseAutoUpdateAfterTransaction(method)) {
//            log.debug("Entity <{}> state changed. It's registered for Database Update", original.getClass().getName());
//        }
//        if(isMethodTransactional){
//            log.debug("Database Transaction Ended for <{}::{}>",original.getClass().getSimpleName(), method.getName());
//        }


    }

//    private boolean isEligibleForDatabaseAutoUpdateAfterTransaction(Method activeMethod) {
//        boolean isSetterMethodCalled = activeMethod.getName().startsWith("set");
//        return this.isEntity && isSetterMethodCalled && Context.isCurrentlyWithinTransaction();
//    }
}
