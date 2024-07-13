package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserDaoJDBCImpl implements UserDao {

    private static final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());

    private String connect = "Connection is closed";

    public UserDaoJDBCImpl() {
        //default implementation
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE user \n" +
                    " (id INT NOT NULL AUTO_INCREMENT,\n" +
                    " name VARCHAR(255),\n" +
                    " lastName VARCHAR(255),\n" +
                    " age TINYINT(3),\n" +
                    " PRIMARY KEY (id))\n" +
                    "ENGINE = InnoDB\n" +
                    "DEFAULT CHARACTER SET = utf8;\n");
            logger.info("Таблица успешно создана.");
        } catch (SQLSyntaxErrorException ex) {
            logger.info("Таблица уже существует!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info(connect);
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE user");
            logger.info("Таблица успешно удалена.");
        } catch (SQLSyntaxErrorException ex) {
            logger.info("Таблицы не существует!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info(connect);
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

            preparedStatement.executeUpdate();

            System.out.println("User с именем - " + name + " добавлен в базу данных.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info(connect);
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info(connect);
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT user.id, user.name, user.lastName, user.age FROM user");
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info(connect);
        System.out.println(userList);
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("TRUNCATE user");
            logger.info("Таблица успешно очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        logger.info(connect);
    }
}
