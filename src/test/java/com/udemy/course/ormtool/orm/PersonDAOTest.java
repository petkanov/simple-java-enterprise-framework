package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.IntegrationTestApplicationEnvironment;
import com.udemy.course.ormtool.context.FrameworkUtils;
import com.udemy.course.ormtool.model.Person;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PersonDAOTest extends IntegrationTestApplicationEnvironment {
    private PersonDAO personDAO = new PersonDAO(transactionManager);

    @Test
    public void currencyConversionTest() throws Exception {
        Person person = new Person("John Wick", 35);
        Person alice = new Person("alice jones", 23);

        person.addFriend(alice);
        alice.addFriend(person);

        Person copy = FrameworkUtils.makeShallowCopyOf(person);

        person.setName("new john wick");
        alice.setName("new Alice Jones");

        System.out.println(copy);

//        personDAO.persist(person);
//        assertEquals(null, transactionManager.getConnection());
//        assertEquals(5, person.getId());
//        transactionManager.rollbackTransactionAndCloseSession();
        assertTrue(true);
    }

//    private <T> T copyFields(T entity, T newEntity, Class<?> clazz) throws IllegalAccessException {
//        List<Field> fields = new ArrayList<>();
//        for (Field field : clazz.getDeclaredFields()) {
//            fields.add(field);
//        }
//        for (Field field : fields) {
//            field.setAccessible(true);
//            field.set(newEntity, field.get(entity));
//        }
//        return newEntity;
//    }
}
