package main.java.jm.task.core.jdbc;

import main.java.jm.task.core.jdbc.model.User;
import main.java.jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // реализуйте алгоритм здесь
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Vova", "Pushkin", (byte) 11);
        userService.saveUser("Vova2", "Pushkin2", (byte) 12);
        userService.saveUser("Vova3", "Pushkin3", (byte) 13);
        userService.saveUser("Vova4", "Pushkin4", (byte) 14);

        List<User> users = userService.getAllUsers();

        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
