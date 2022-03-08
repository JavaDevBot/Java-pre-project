package main.java.jm.task.core.jdbc.dao;

import main.java.jm.task.core.jdbc.model.User;
import main.java.jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util utilConnection;

    public UserDaoJDBCImpl() {
        try {
            utilConnection = new Util("root", "rootroot",
                    "jdbc:mysql://localhost:3306/phones_magazine");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUsersTable() {
        if (utilConnection != null) {
            try (Statement statement = utilConnection.getConnection()
                    .createStatement()) {
                statement.execute("CREATE TABLE IF NOT EXISTS Users\n" +
                        "(\n" +
                        "    id       INT AUTO_INCREMENT,\n" +
                        "    name     VARCHAR(50),\n" +
                        "    lastName VARCHAR(50),\n" +
                        "    age      TINYINT UNSIGNED,\n" +
                        "    PRIMARY KEY (id)\n" +
                        ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        if (utilConnection != null) {
            try (Statement statement = utilConnection.getConnection().createStatement()) {
                statement.execute("DROP TABLE IF EXISTS Users;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        if (utilConnection != null) {
            try (Statement statement = utilConnection.getConnection().createStatement()) {
                statement.executeUpdate("INSERT INTO Users(name, lastName, age)\n" +
                        "VALUES ('" + name + "', '" + lastName + "', " + age + ");");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        if (utilConnection != null) {
            try (Statement statement = utilConnection.getConnection().createStatement()) {
                statement.executeUpdate("DELETE FROM Users\n" +
                        "WHERE id = " + id + ";");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        if (utilConnection != null) {
            List<User> allUsers = new ArrayList<>();

            try (Statement statement = utilConnection.getConnection().createStatement()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM Users;");

                while (resultSet.next()) {
                    allUsers.add(new User(
                            resultSet.getString("name"),
                            resultSet.getString("lastName"),
                            resultSet.getByte("id")));
                }

                return allUsers;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void cleanUsersTable() {
        if (utilConnection != null) {
            try (Statement statement = utilConnection.getConnection().createStatement()) {
                statement.executeUpdate("TRUNCATE TABLE Users;");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
