package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.context.Environment;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Bean
public class ConnectionProviderImpl implements ConnectionProvider {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final BasicDataSource dataSource;

    public ConnectionProviderImpl(Environment environment) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty("database.driver-name"));
        dataSource.setUrl(environment.getProperty("database.url"));
        dataSource.setUsername(environment.getProperty("database.username"));
        dataSource.setPassword(environment.getProperty("database.password"));
        this.dataSource = dataSource;
    }

    public void printData() {
        try {
            Connection con = dataSource.getConnection();

            Statement stmt = con.createStatement();

            ResultSet rs = stmt.executeQuery("select * from person");

            while (rs.next())
                log.debug(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));

            con.close();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
