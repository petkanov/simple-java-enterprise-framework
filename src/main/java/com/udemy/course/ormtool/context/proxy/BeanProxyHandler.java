package com.udemy.course.ormtool.context.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class BeanProxyHandler implements MethodInterceptor {
    private final CallbackStrategyResolver callbackStrategyResolver;

    public BeanProxyHandler(CallbackStrategyResolver callbackStrategyResolver) {
        this.callbackStrategyResolver = callbackStrategyResolver;
    }

    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

        callbackStrategyResolver.preMethodCallback(object, method);

        Object originalMethodCallResult = methodProxy.invokeSuper(object, args);

        callbackStrategyResolver.afterMethodCallback(object, method);

        return originalMethodCallResult;
    }
}
