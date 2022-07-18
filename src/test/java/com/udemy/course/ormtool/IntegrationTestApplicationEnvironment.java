package com.udemy.course.ormtool;

import com.udemy.course.ormtool.context.Environment;
import com.udemy.course.ormtool.orm.*;

public abstract class IntegrationTestApplicationEnvironment {
    protected Environment environment = new Environment("app.properties");
    protected ConnectionProvider connectionProvider = new ConnectionProviderImpl(environment);
    protected UnitOfWork unitOfWork = new UnitOfWork();
    protected DaoRegistry daoRegistry = new DaoRegistry();
    protected TransactionManager transactionManager = new TransactionManager(connectionProvider, unitOfWork);
    protected EntityManager entityManager = new EntityManager(transactionManager, daoRegistry, unitOfWork);
}
