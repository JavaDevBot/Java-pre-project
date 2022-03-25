package main.java.jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    // реализуйте настройку соеденения с БД

    private final String userName;
    private final String userPassword;
    private final String connectionURL;
    private final String DATABASE_DRIVER;

    public static enum Drivers {
        MYSQL("com.mysql.cj.jdbc.Driver");

        private final String DATABASE_DRIVER;

        Drivers(String driver) {
            this.DATABASE_DRIVER = driver;
        }

        String getDriver() {
            return DATABASE_DRIVER;
        }
    }

    public Util(String userName, String userPassword, String connectionURL, Drivers driver) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.connectionURL = connectionURL;
        this.DATABASE_DRIVER = driver.getDriver();
    }

    public Connection createConnection() {
        try {
            Class.forName(DATABASE_DRIVER);
            Connection connection = DriverManager.getConnection(connectionURL, userName, userPassword);
            connection.setAutoCommit(false);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
