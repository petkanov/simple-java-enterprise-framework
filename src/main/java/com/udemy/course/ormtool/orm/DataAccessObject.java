package com.udemy.course.ormtool.orm;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public abstract class DataAccessObject<T> {
    private final IdentityMap<T> cache;
    private final TransactionManager transactionManager;

    public DataAccessObject(TransactionManager transactionManager) {
        cache = new IdentityMap<>();
        this.transactionManager = transactionManager;
    }

    public abstract boolean persist(T object) throws SQLException;

    public abstract boolean update(T object) throws SQLException;

    public abstract List<T> getAllForID(int parentId) throws SQLException;

    public abstract boolean delete(Integer objectId) throws SQLException;

    public abstract boolean delete(T obj) throws SQLException;

    public abstract T get(int objectId) throws SQLException;

    public abstract Map<Integer, T> getAll() throws SQLException;

    public abstract void restoreObjectState(T object) throws SQLException;

    protected IdentityMap<T> getCache() {
        return cache;
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }
}
