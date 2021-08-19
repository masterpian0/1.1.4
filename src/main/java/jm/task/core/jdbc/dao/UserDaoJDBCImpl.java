package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }
    public void createUsersTable() {
       String query = "CREATE TABLE IF NOT EXISTS users (\n" +
               "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
               "  name NVARCHAR(45),\n" +
               "  lastname NVARCHAR(45),\n" +
               "  age TINYINT,\n" +
               "  PRIMARY KEY (id));\n";
       try (Statement statement = Util.getConnection().createStatement()) {
           statement.execute(query);
       } catch (SQLException e) {
           System.out.println("Не удалось создать таблицу");
       }
    }

    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO users(name, lastName, age) VALUES (?,?,?);";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.execute();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Не удалось добавить в базу, User с именем - " + name);
        }
    }

    public void removeUserById(long id) {
        String query = "DELETE FROM users WHERE id = ?;";
        try (PreparedStatement statement = Util.getConnection().prepareStatement(query)) {
            statement.setLong(1, id);
            statement.execute();
            System.out.println("User с id " + id + "  удален из базы данных");
        } catch (SQLException e) {
            System.out.println("Не удалось удалить из базы, User с id" + id);
        }
    }

    public void cleanUsersTable() {
        String query = "DELETE FROM users;";
        try (Statement statement = Util.getConnection().createStatement()) {
            statement.execute(query);
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу");
        }
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        try (Statement statement = Util.getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet != null && resultSet.next()) {
                long id = resultSet.getLong("id");
                String username = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                byte age = resultSet.getByte("age");
                userList.add(new User(id, username, lastname, age));
            }
        } catch (SQLException e) {
            System.out.println("Не удалось получить всех данные из базы");
        }
        return userList;
    }
}