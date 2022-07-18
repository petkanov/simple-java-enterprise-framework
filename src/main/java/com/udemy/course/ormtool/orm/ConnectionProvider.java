package com.udemy.course.ormtool.orm;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
    void printData();

    Connection getConnection() throws SQLException;
}
