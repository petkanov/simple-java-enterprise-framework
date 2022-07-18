package com.udemy.course.ormtool.context;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.context.proxy.CallbackStrategyResolver;
import com.udemy.course.ormtool.context.proxy.ProxyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Framework {
    static Logger log = LoggerFactory.getLogger(Framework.class);
    public static final String APPLICATION_PROPERTIES = "app.properties";

    private static final Map<Class<?>, Object> IMPLEMENTATION_TO_INSTANCE = new ConcurrentHashMap();
    private static final  Map<Class<?>, Class<?>> INTERFACE_TO_IMPLEMENTATION = new HashMap<>();

    public static void runApplication(String[] args) throws Exception {

        loadFrameworkBeans();

        final String javaRootFolder = Paths.get("").toAbsolutePath()
                .resolve(String.format("src%smain%sjava", File.separator, File.separator))
                .toString();

        final Set<Class<?>> userBeanClasses = new HashSet<>();

        Files.walk(Paths.get(javaRootFolder))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".java"))
                .forEach(path -> {

                    String classname = path.toString()
                            .replace(javaRootFolder.concat(File.separator), "")
                            .replace(File.separator, ".")
                            .replace(".java", "");

                    Class clazz;
                    try {
                        clazz = Class.forName(classname);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    if (!clazz.isInterface() && clazz.getAnnotation(Bean.class) != null) {

                        userBeanClasses.add(clazz);

                        Class[] interfaces = clazz.getInterfaces();
                        if (interfaces.length > 0) {
                            INTERFACE_TO_IMPLEMENTATION.putIfAbsent(interfaces[0], clazz);
                        } else {
                            INTERFACE_TO_IMPLEMENTATION.put(clazz, clazz);
                        }
                    }
                    log.trace("User Bean CLass found: {}", classname);
                });

        log.debug("User Bean Class Objects: {}", userBeanClasses);

        Set<Class<?>> initialFrameworkBeans = IMPLEMENTATION_TO_INSTANCE.keySet();
        List<Class<?>> userBeansInTopologicalOrder = GraphUtils.topologicalSortOf(userBeanClasses, initialFrameworkBeans);

        log.debug("User Beans in Topological Order: {}", userBeansInTopologicalOrder);

        ProxyFactory proxyFactory = getBeanInstance(ProxyFactory.class);
        userBeansInTopologicalOrder.forEach( clazz -> {
            Object instance = proxyFactory.instantiateBean(clazz);
            IMPLEMENTATION_TO_INSTANCE.put(clazz, instance);
        });

        log.debug("Application Context Objects: {}", IMPLEMENTATION_TO_INSTANCE);

        runUserApplication(args);
    }

    public static <T> T getBeanInstance(Class<?> type) {
        Class<?> implementationType = type;
        if (type.isInterface()) {
            implementationType = INTERFACE_TO_IMPLEMENTATION.get(type);
            if (implementationType == null) {
                log.warn("Missing Implementation For Interface {}", type);
                throw new RuntimeException("No Implementation For Interface");
            }
        }
        Object beanInstance = IMPLEMENTATION_TO_INSTANCE.get(implementationType);
        if (beanInstance == null) {
            log.error("Missing Bean Instance For Type {}", implementationType);
            throw new RuntimeException("Missing Bean In Application Context");
        }
        return (T) beanInstance;
    }

    private static void loadFrameworkBeans() {
        IMPLEMENTATION_TO_INSTANCE.put(Environment.class, new Environment(APPLICATION_PROPERTIES));
        IMPLEMENTATION_TO_INSTANCE.put(CallbackStrategyResolver.class, new CallbackStrategyResolver());
        IMPLEMENTATION_TO_INSTANCE.put(ProxyFactory.class, new ProxyFactory());
    }

    private static void runUserApplication(String[] args) {
        try {
            ApplicationRunner userApplication = getBeanInstance(ApplicationRunner.class);
            userApplication.run(args);
        } catch (Exception e) {
            log.info("There is no User Defined Logic to Run. Exit");
        }
    }
}