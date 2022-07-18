package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.annotations.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

@Bean
public class UnitOfWork {
    private static final Logger log = LoggerFactory.getLogger(UnitOfWork.class);

    private final ThreadLocal<UnitOfWork> current = new ThreadLocal<>();

    private final Queue<Object> newObjects     = new LinkedList<>();
    private final Queue<Object> updatedObjects = new LinkedList<>();
    private final Queue<Object> deletedObjects = new LinkedList<>();

    public UnitOfWork getCurrent() {
        if (current.get() == null) {
            current.set(new UnitOfWork());
        }
        return current.get();
    }

    public void registerForUpdate(Object object) {
        if (!newObjects.contains(object) && !updatedObjects.contains(object)) {
            updatedObjects.add(object);
        }
    }

    public void registerForDelete(Object object) {
        newObjects.remove(object);
        updatedObjects.remove(object);
        if (!deletedObjects.contains(object)) {
            deletedObjects.add(object);
        }
    }

    public void commitSession() throws SQLException {
        updateRegistered();
        deleteRegistered();
    }

    public void restoreRegisteredObjectsState() {
        while (!updatedObjects.isEmpty()) {
            final Object obj = updatedObjects.remove();
//            try {
//                DaoRegistry.getInstance().getDAO(obj.getClass().getName()).restoreObjectState(obj);
//            } catch (SQLException e) {
//                log.error("Object {} state was not restored", obj);
//            }
        }
    }

    private void updateRegistered() throws SQLException {
        while (!updatedObjects.isEmpty()) {
            final Object obj = updatedObjects.remove();
//            DaoRegistry.getInstance().getDAO(obj.getClass().getName()).update(obj);
        }
    }

    private void deleteRegistered() throws SQLException {
        while (!deletedObjects.isEmpty()) {
            final Object obj = deletedObjects.remove();
//            DaoRegistry.getInstance().getDAO(obj.getClass().getName()).delete(obj);
        }
    }

    public void unregisterObject(Object object) {
        updatedObjects.remove(object);
    }
}
