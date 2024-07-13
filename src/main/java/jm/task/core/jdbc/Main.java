package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 28);
        userService.saveUser("Petr", "Petrovich", (byte) 18);
        userService.saveUser("Oleg", "Olegovich", (byte) 35);
        userService.saveUser("Igor", "Igorevich", (byte) 41);

        System.out.println(userService.getAllUsers());

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}
