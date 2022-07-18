package com.udemy.course.ormtool.service;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.context.ApplicationRunner;
import com.udemy.course.ormtool.orm.ConnectionProvider;
import com.udemy.course.ormtool.orm.TransactionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Bean
public class Application implements ApplicationRunner {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConnectionProvider dataProvider;
    private final TransactionManager transactionManager;
//    private final PersonDAO personDAO;

    public Application(ConnectionProvider dataProvider, TransactionManager transactionManager) {//, PersonDAO personDAO) {
        this.dataProvider = dataProvider;
        this.transactionManager = transactionManager;
//        this.personDAO = personDAO;
    }

    @Override
    public void run(String[] args) {
        dataProvider.printData();

        try {
            transactionManager.initiateTransactionAndOpenSession();
//            log.info(personDAO.getTransactionManager().getConnection().getMetaData().toString());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }
}
