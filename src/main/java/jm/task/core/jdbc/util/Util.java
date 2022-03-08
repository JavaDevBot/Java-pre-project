package main.java.jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД
    private String userName;
    private String userPassword;
    private String connectionURL;
    private Connection connection;

    public Util(String userName, String userPassword, String connectionURL) throws SQLException {
        this.userName = userName;
        this.userPassword = userPassword;
        this.connectionURL = connectionURL;
        createConnection();
    }

    private void createConnection() throws SQLException {
        connection = DriverManager.getConnection(connectionURL, userName, userPassword);
    }

    public Connection getConnection() {
        return connection;
    }
}
