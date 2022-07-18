package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.annotations.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

@Bean
public class TransactionManager {
    private static final Logger log = LoggerFactory.getLogger(TransactionManager.class);

    private final ConnectionProvider connectionProvider;
    private final UnitOfWork unitOfWork;

    private static final ThreadLocal<Connection> transactionConnection = new ThreadLocal<>();
    private static final ThreadLocal<Boolean> isSessionOpen = new ThreadLocal<>();

    public TransactionManager(ConnectionProvider connectionProvider, UnitOfWork unitOfWork) {
        this.connectionProvider = connectionProvider;
        this.unitOfWork = unitOfWork;
    }

    public void initiateTransactionAndOpenSession() throws SQLException {
        final Connection conn = connectionProvider.getConnection();
        conn.setAutoCommit(false);
        transactionConnection.set(conn);
        isSessionOpen.set(true);
    }

    public void commitTransactionAndCloseSession() throws SQLException {
        final Connection conn = transactionConnection.get();
        try {
            conn.commit();
        } finally {
            transactionConnection.remove();
            isSessionOpen.set(false);
            closeConnection(conn);
        }
    }

    public void rollbackTransactionAndCloseSession() {
        final Connection conn = transactionConnection.get();
        try {
            conn.rollback();
        } catch (SQLException e) {
            log.error("Transaction rollback failed: {}. Restoring domain objects state..", e.getMessage());
            unitOfWork.getCurrent().restoreRegisteredObjectsState();
        } finally {
            transactionConnection.remove();
            isSessionOpen.set(false);
            closeConnection(conn);
        }
    }

    public void initiateTransaction() throws SQLException {
        if (isSessionOpen.get()) {
            return;
        }
        if (transactionConnection.get() == null) {
            final Connection conn = connectionProvider.getConnection();
            conn.setAutoCommit(false);
            transactionConnection.set(conn);
        }
    }

    public void commitTransaction() throws SQLException {
        if (isSessionOpen.get()) {
            return;
        }
        final Connection conn = transactionConnection.get();
        try {
            conn.commit();
        } finally {
            transactionConnection.remove();
            closeConnection(conn);
        }
    }

    public Connection getConnection() throws SQLException {
        if (!isSessionOpen.get()) {
            return connectionProvider.getConnection();
        }
        return transactionConnection.get();
    }

    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }
}
