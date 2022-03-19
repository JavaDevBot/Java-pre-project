package main.java.jm.task.core.jdbc.dao;

import main.java.jm.task.core.jdbc.model.User;
import main.java.jm.task.core.jdbc.util.Util;

import java.sql.*;
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
        final String createTable = "CREATE TABLE IF NOT EXISTS Users\n" +
                "(\n" +
                "    id       INT AUTO_INCREMENT,\n" +
                "    name     VARCHAR(50),\n" +
                "    lastName VARCHAR(50),\n" +
                "    age      TINYINT UNSIGNED,\n" +
                "    PRIMARY KEY (id)\n" +
                ");";

        if (utilConnection != null) {
            try (PreparedStatement statement = utilConnection.getConnection()
                    .prepareStatement(createTable)) {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        final String dropTable = "DROP TABLE IF EXISTS Users;";

        if (utilConnection != null) {
            try (PreparedStatement statement = utilConnection.getConnection().prepareStatement(dropTable)) {
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String saveUser = "INSERT INTO Users(name, lastName, age)\n" +
                "VALUES (?, ?, ?);";

        if (utilConnection != null) {
            try (PreparedStatement statement = utilConnection.getConnection().prepareStatement(saveUser)) {
                statement.setString(1, name);
                statement.setString(2, lastName);
                statement.setByte(3, age);

                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        final String removeUser = "DELETE FROM Users\n" +
                "WHERE id = ?;";

        if (utilConnection != null) {
            try (PreparedStatement statement = utilConnection.getConnection().prepareStatement(removeUser)) {
                statement.setLong(1, id);
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        if (utilConnection != null) {
            List<User> allUsers = new ArrayList<>();
            final String getAllUsers = "SELECT * FROM Users;";

            try (PreparedStatement statement = utilConnection.getConnection().prepareStatement(getAllUsers)) {
                ResultSet resultSet = statement.executeQuery();

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
            final String cleanTable = "TRUNCATE TABLE Users;";

            try (PreparedStatement statement = utilConnection.getConnection().prepareStatement(cleanTable)) {
                statement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
