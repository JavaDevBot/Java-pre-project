package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private final String userName;
    private final String userPassword;
    private final String connectionURL;
    private final String DATABASE_DRIVER;
    private static SessionFactory sessionFactory;

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

    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DATABASE_DRIVER);
                settings.put(Environment.URL, connectionURL);
                settings.put(Environment.USER, userName);
                settings.put(Environment.PASS, userPassword);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");

                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

                settings.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
