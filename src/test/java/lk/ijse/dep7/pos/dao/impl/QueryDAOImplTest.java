package lk.ijse.dep7.pos.dao.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class QueryDAOImplTest {

    private QueryDAOImpl queryDAOImpl;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep7_pos","root","mysql");
        queryDAOImpl = new QueryDAOImpl(connection);
    }

    @Test
    void searchOrders() throws Exception {
        queryDAOImpl.findOrders("", 1, 5).forEach(System.out::println);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }
}