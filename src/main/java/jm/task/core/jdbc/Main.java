package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;

public class Main {

    public static void main(String[] args) {

        UserDaoHibernateImpl userService = new UserDaoHibernateImpl();

        userService.createUsersTable();
        userService.saveUser("Max","First", (byte) 40);
        userService.saveUser("Daniel","Second", (byte) 25);
        userService.saveUser("Alex","Third", (byte) 18);
        userService.saveUser("Shimon","Fourth", (byte) 4);

        userService.getAllUsers().forEach(System.out::println);

        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
