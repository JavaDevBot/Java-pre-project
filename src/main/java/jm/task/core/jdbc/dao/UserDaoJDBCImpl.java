package jm.task.core.jdbc.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

public class UserDaoJDBCImpl implements UserDao {
    private final Util mysqlUtil;

    public UserDaoJDBCImpl() {
        mysqlUtil = new Util("root", "rootroot",
                "jdbc:mysql://localhost:3306/phones_magazine", Util.Drivers.MYSQL);
    }

    public void createUsersTable() {
        try (Connection connection = mysqlUtil.createConnection()) {
            final String createTable = "CREATE TABLE IF NOT EXISTS Users\n" +
                    "(\n" +
                    "    id       INT AUTO_INCREMENT,\n" +
                    "    name     VARCHAR(50),\n" +
                    "    lastName VARCHAR(50),\n" +
                    "    age      TINYINT UNSIGNED,\n" +
                    "    PRIMARY KEY (id)\n" +
                    ");";

            connection.setAutoCommit(false);

            try (PreparedStatement statement = mysqlUtil.createConnection()
                    .prepareStatement(createTable)) {
                statement.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        try (Connection connection = mysqlUtil.createConnection()) {
            final String dropTable = "DROP TABLE IF EXISTS Users;";

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(dropTable)) {
                statement.execute();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = mysqlUtil.createConnection()) {
            final String saveUser = "INSERT INTO Users(name, lastName, age)\n" +
                    "VALUES (?, ?, ?);";

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(saveUser)) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);

                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = mysqlUtil.createConnection()) {
            final String removeUser = "DELETE FROM Users\n" +
                    "WHERE id = ?;";

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(removeUser)) {
                statement.setLong(1, id);
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        try (Connection connection = mysqlUtil.createConnection()) {
            List<User> allUsers = new ArrayList<>();
            final String getAllUsers = "SELECT * FROM Users;";

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(getAllUsers)) {
                ResultSet resultSet = statement.executeQuery();
                connection.commit();

                while (resultSet.next()) {
                    allUsers.add(new User(
                            resultSet.getString("name"),
                            resultSet.getString("lastName"),
                            resultSet.getByte("id")));
                }

                return allUsers;
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void cleanUsersTable() {
        try (Connection connection = mysqlUtil.createConnection()) {
            final String cleanTable = "TRUNCATE TABLE Users;";

            connection.setAutoCommit(false);

            try (PreparedStatement statement = connection.prepareStatement(cleanTable)) {
                statement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
