package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.annotations.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Bean
public class EntityManager {
    private static final Logger log = LoggerFactory.getLogger(UnitOfWork.class);

    private final TransactionManager transactionManager;
    private final DaoRegistry daoRegistry;
    private final UnitOfWork unitOfWork;

    public EntityManager(TransactionManager transactionManager, DaoRegistry daoRegistry, UnitOfWork unitOfWork) {
        this.transactionManager = transactionManager;
        this.daoRegistry = daoRegistry;
        this.unitOfWork = unitOfWork;
    }

    public void registerForUpdate(Object object) {
        unitOfWork.getCurrent().registerForUpdate(object);
    }

    public void registerForDelete(Object object) {
        unitOfWork.getCurrent().registerForDelete(object);
    }

    public boolean commitTransactionRegisteredWork() {
        try {
            transactionManager.initiateTransactionAndOpenSession();
            unitOfWork.getCurrent().commitSession();
            transactionManager.commitTransactionAndCloseSession();
            return true;
        }catch(Exception e) {
            log.error(e.getMessage());
            transactionManager.rollbackTransactionAndCloseSession();
            return false;
        }
    }

    public boolean persistObject(Object object) {
        try {
            transactionManager.initiateTransaction();
            daoRegistry.getDAO(object.getClass().getName()).persist(object);
            transactionManager.commitTransaction();
            return true;
        }catch(Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean updateObject(Object object) {
        try {
            transactionManager.initiateTransaction();
            daoRegistry.getDAO(object.getClass().getName()).update(object);
            transactionManager.commitTransaction();
            return true;
        }catch(Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public boolean deleteObjectById(Class<?> objectClass, Integer objectId) {
        try {
            transactionManager.initiateTransaction();
            daoRegistry.getDAO(objectClass.getName()).delete(objectId);
            transactionManager.commitTransaction();
            return true;
        }catch(Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public <T> T getObjectById(Class<?> objectClass, int objectId) {
        try {
            transactionManager.initiateTransactionAndOpenSession();
            final T object = (T) daoRegistry.getDAO(objectClass.getName()).get(objectId);
            transactionManager.commitTransactionAndCloseSession();
            return object;
        }catch(Exception e) {
            log.error(e.getMessage());
            transactionManager.rollbackTransactionAndCloseSession();
            return null;
        }
    }

    public <T> Map<Integer, T> getAllObjects(Class<?> objectClass) {
        Map<Integer, T> objects = null;
        try {
            transactionManager.initiateTransactionAndOpenSession();
            objects = (Map<Integer, T>) daoRegistry.getDAO(objectClass.getName()).getAll();
            transactionManager.commitTransactionAndCloseSession();
        }catch(Exception e) {
            log.error(e.getMessage());
            transactionManager.rollbackTransactionAndCloseSession();
        }
        return objects;
    }

    public <T> List<T> getAllObjectsForID(Class<?> objectClass, int parentId) {
        List<T> objects = null;
        try {
            transactionManager.initiateTransactionAndOpenSession();
            objects = (List<T>) daoRegistry.getDAO(objectClass.getName()).getAllForID(parentId);
            transactionManager.commitTransactionAndCloseSession();
        }catch(Exception e) {
            log.error(e.getMessage());
            transactionManager.rollbackTransactionAndCloseSession();
        }
        return objects;
    }
}
