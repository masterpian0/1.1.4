package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

// === JDBC Framework ===
public class Util {
    private static Connection connection;

    final static String DB_URL = "jdbc:mysql://localhost:3306/my_db";
    final static String DB_USERNAME = "root";
    final static String DB_PASSWORD = "springcourse";

    public static Connection getConnection() {
       if (connection == null) {
           try {
               connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
           } catch (SQLException e) {
               System.out.println("Не удалось создать соединение с БД");
           }
       }
       return connection;
    }

// === Hibernate Framework ===

    public static SessionFactory createSessionFactory() {

        Configuration cfg = new Configuration()
                .setProperty("hibernate.connection.url",
                        "jdbc:mysql://localhost:3306/my_db?useSSL=false&amp;serverTimezone=UTC")
                .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                .setProperty("hibernate.connection.username", "root")
                .setProperty("hibernate.connection.password", "springcourse")
                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .configure()
                .addAnnotatedClass(User.class);

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(cfg.getProperties())
                .build();
        return cfg.buildSessionFactory(serviceRegistry);
    }
}