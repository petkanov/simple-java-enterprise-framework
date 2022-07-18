package com.udemy.course.ormtool.abcdef;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.orm.ConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Bean
public class E { // implements ApplicationRunner {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final ConnectionProvider dataProvider;
    private B bi;
    private DI di;

    public E (B bi, DI di, ConnectionProvider dataProvider){
        this.bi = bi;
        this.di = di;
        this.dataProvider = dataProvider;
    }

    public String callMe() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return bi.toString() + " | " + di.toString();
    }

//    @Override
    public void run(String[] args) {
        dataProvider.printData();
        log.info("-------------- dataProvider.toString() {}", dataProvider);
    }
}
