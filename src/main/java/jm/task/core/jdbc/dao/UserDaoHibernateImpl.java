package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import static jm.task.core.jdbc.util.Util.createSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    SessionFactory factory = createSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        String query = "CREATE TABLE IF NOT EXISTS users (\n" +
                "  id BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  name NVARCHAR(45),\n" +
                "  lastname NVARCHAR(45),\n" +
                "  age TINYINT,\n" +
                "  PRIMARY KEY (id));\n";
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(query)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
            System.out.println("Таблица создана");
        } catch (HibernateException e) {
            System.out.println("Не удалось создать таблицу");
        }
    }

    @Override
    public void dropUsersTable() {
        String query = "DROP TABLE IF EXISTS users;";
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(query)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
            System.out.println("Таблица удалена");
        } catch (HibernateException e) {
            System.out.println("Не удалось удалить таблицу");
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction()
                    .commit();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            System.out.println("Не удалось добавить в базу, User с именем - " + name);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            User user = (User) session.get(User.class, id);
            session.delete(user);
            session.getTransaction()
                    .commit();
        System.out.println("User с id " + id + "  удален из базы данных");
        } catch (HibernateException e) {
            System.out.println("Не удалось удалить из базы, User с id" + id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        String query = "SELECT * FROM users";
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            List<User> result = session.createSQLQuery(query)
                    .addEntity(User.class)
                    .list();
            session.getTransaction()
                    .commit();
            System.out.println("Все данные из базы получены");
            return result;
        } catch (HibernateException e) {
            System.out.println("Не удалось получить всех данные из базы");
            return null;
        }
    }

    @Override
    public void cleanUsersTable() {
        String query = "DELETE FROM users;";
        try (Session session = factory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(query)
                    .executeUpdate();
            session.getTransaction()
                    .commit();
            System.out.println("Содержание таблицы очищено");
        } catch (HibernateException e) {
            System.out.println("Не удалось очистить таблицу");
        }
    }
}
