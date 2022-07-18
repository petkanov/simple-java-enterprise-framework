package com.udemy.course.ormtool.context;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FrameworkUtils {

    public static  <T> T makeShallowCopyOf(T object) throws Exception {
        T copyObject = (T) object.getClass().getDeclaredConstructor().newInstance();

        Class<?> clazz = object.getClass();
        while (clazz != null) {
            List<Field> fields = new ArrayList<>();

            for (Field field : clazz.getDeclaredFields()) {
                fields.add(field);
            }
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(copyObject, field.get(object));
            }

            clazz = clazz.getSuperclass();
        }
        return copyObject;
    }
}
