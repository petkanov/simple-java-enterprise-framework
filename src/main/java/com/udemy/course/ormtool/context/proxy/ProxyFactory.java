package com.udemy.course.ormtool.context.proxy;

import com.udemy.course.ormtool.context.Framework;
import net.sf.cglib.proxy.Enhancer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

public class ProxyFactory {
    private static final Logger log = LoggerFactory.getLogger(ProxyFactory.class);

    public Object instantiateBean(Class<?> type) {
        log.debug("Creating Instance of {}", type);
        try {
            Class[] argsClasses = type.getConstructors()[0].getParameterTypes();
            log.debug("Type {}, Constructor ParamTypes: {}", type, List.of(argsClasses));

            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(type);
            enhancer.setCallback(new BeanProxyHandler( Framework.getBeanInstance(CallbackStrategyResolver.class)));

            Object[] args = getBeanConstructorArguments(type);

            return enhancer.create(argsClasses, args);

        } catch (Exception e) {
            log.error("Could Not Instantiate Bean {}", type);
            throw new RuntimeException(e);
        }
    }

    private Object[] getBeanConstructorArguments(Class<?> bean) {
        final List<Object> constructorArgumentBeans = new LinkedList<>();

        Class<?>[] constructorArguments = bean.getConstructors()[0].getParameterTypes();

        if (constructorArguments.length > 0) {
            for (Class<?> constructorArgument : constructorArguments) {

                constructorArgumentBeans.add( Framework.getBeanInstance(constructorArgument));
                log.trace("ParamType {}, Value {}", constructorArgument, Framework.getBeanInstance(constructorArgument));
            }
        }
        return constructorArgumentBeans.toArray();
    }
}

