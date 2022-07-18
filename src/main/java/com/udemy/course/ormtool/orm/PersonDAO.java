package com.udemy.course.ormtool.orm;

import com.udemy.course.ormtool.annotations.Bean;
import com.udemy.course.ormtool.model.Person;

import java.sql.*;
import java.util.List;
import java.util.Map;

//@Bean
public class PersonDAO extends DataAccessObject<Person> {

    public PersonDAO(TransactionManager transactionManager) {
        super(transactionManager);
    }

    @Override
    public boolean persist(Person person) throws SQLException {
        final Connection conn = getTransactionManager().getConnection();

        String query = "INSERT INTO person (name, age) VALUES (?,?)";

        PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, person.getName() != null ? person.getName() : "");
        statement.setInt(2, person.getAge());

        statement.executeUpdate();

        final ResultSet rs = statement.getGeneratedKeys();
        rs.next();
        final int lastInsertedUserId = rs.getInt(1);
        rs.close();
        statement.close();
        person.setId(lastInsertedUserId);
        return true;
    }

    @Override
    public boolean update(Person object) throws SQLException {
        return false;
    }

    @Override
    public List<Person> getAllForID(int parentId) throws SQLException {
        return null;
    }

    @Override
    public boolean delete(Integer objectId) throws SQLException {
        return false;
    }

    @Override
    public boolean delete(Person obj) throws SQLException {
        return false;
    }

    @Override
    public Person get(int objectId) throws SQLException {
        return null;
    }

    @Override
    public Map<Integer, Person> getAll() throws SQLException {
        return null;
    }

    @Override
    public void restoreObjectState(Person object) throws SQLException {

    }
}
