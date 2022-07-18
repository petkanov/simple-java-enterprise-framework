package com.udemy.course.ormtool.context;

import com.udemy.course.ormtool.annotations.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.stream.Collectors;

public class GraphUtils {
    static Logger log = LoggerFactory.getLogger(GraphUtils.class);

    public static List<Class<?>> topologicalSortOf(Set<Class<?>> userBeans, Set<Class<?>> initialFrameworkBeans) {
        Map<Class<?>, Set<Class<?>>> typeToDependants = new HashMap<>();

        userBeans.forEach( userBean -> {
            typeToDependants.putIfAbsent(userBean, new HashSet<>());

            Constructor beanConstructor = userBean.getConstructors()[0];
            List<Class<?>> constructorParameterTypes = new ArrayList(List.of(beanConstructor.getParameterTypes()));

            constructorParameterTypes = constructorParameterTypes.stream()
                    .filter(paramBean -> !initialFrameworkBeans.contains(paramBean))
                    .filter(paramBean -> paramBean.isInterface() || paramBean.getAnnotation(Bean.class) != null)
                    .collect(Collectors.toList());

            constructorParameterTypes.forEach( paramType -> {
                Set<Class<?>> paramTypeDependants = typeToDependants.putIfAbsent(paramType, new HashSet<>(Set.of(userBean)));
                if (paramTypeDependants != null) {
                    paramTypeDependants.add(userBean);
                }
            });
        });

        Map<Class<?>, Set<Class<?>>> beanToDependantBeansGraph = typeToDependants.entrySet()
                .stream()
                .filter(entry -> !entry.getKey().isInterface())
                .collect(Collectors.toMap(
                        entry -> entry.getKey(),
                        entry -> {
                            if (entry.getKey().getInterfaces().length > 0)
                            {
                                Class<?> beanInterface = entry.getKey().getInterfaces()[0];
                                Class<?> beanImplementation = entry.getKey();
                                Set<Class<?>> interfaceTypeDependants = typeToDependants.get(beanInterface);
                                Set<Class<?>> implementationTypeDependants = typeToDependants.get(beanImplementation);
                                if (interfaceTypeDependants != null) {
                                    implementationTypeDependants.addAll( interfaceTypeDependants);
                                }
                                return implementationTypeDependants;
                            }
                            return entry.getValue();
                        } )
                );

        Map<Class<?>, Integer> beanToNumberOfUnresolvedDependencies = new HashMap<>();

        beanToDependantBeansGraph.forEach( (bean, setOfDependants) -> {
            beanToNumberOfUnresolvedDependencies.putIfAbsent(bean, 0);

            setOfDependants.forEach( dependant -> {
                Integer currentValue = beanToNumberOfUnresolvedDependencies.putIfAbsent(dependant, 1);
                if (currentValue != null) {
                    beanToNumberOfUnresolvedDependencies.put(dependant, ++currentValue);
                }
            });
        });


        Queue<Class<?>> beansWithDependenciesResolved = new LinkedList<>();

        beanToNumberOfUnresolvedDependencies.forEach( (bean, dependenciesLeftToResolve) -> {
            if (dependenciesLeftToResolve.equals(0)) {
                beansWithDependenciesResolved.add(bean);
            }
        });

        int resolvedBeans = 0;
        List<Class<?>> beansInTopologicalOrder = new LinkedList<>();

        while (!beansWithDependenciesResolved.isEmpty()) {
            Class<?> resolvedBean = beansWithDependenciesResolved.remove();
            beansInTopologicalOrder.add(resolvedBean);
            resolvedBeans++;

            beanToDependantBeansGraph.get(resolvedBean).forEach(dependantBean -> {
                Integer dependenciesLeft = beanToNumberOfUnresolvedDependencies.get(dependantBean) - 1;
                beanToNumberOfUnresolvedDependencies.put(dependantBean, dependenciesLeft);

                if (dependenciesLeft.equals(0)) {
                    beansWithDependenciesResolved.add(dependantBean);
                }
            });
        }
        if (resolvedBeans != beanToDependantBeansGraph.size()) {
            throw new RuntimeException("Cyclic Reference Detected in Beans While Building Application Context");
        }

        return beansInTopologicalOrder;
    }
}
