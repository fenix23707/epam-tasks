package by.vsu.di;

import by.vsu.dao.UserDao;
import by.vsu.dao.mysql.UserDaoImpl;
import by.vsu.pool.ConnectionPool;
import by.vsu.pool.ConnectionPoolException;
import by.vsu.service.UserService;
import by.vsu.service.UserServiceImpl;

import java.sql.Connection;

/**
 * Создает и инициализирует сервисы выбирая конкретные реализации для них.
 * Также позволяет не создавать лишние объекты.
 *
 * @author Kovzov Vladislav
 */
public class ServiceCreator implements AutoCloseable{
    private UserService userService = null;

    /**
     * Создает объект UserService с заранее выбранной реализацией. А так же
     * добавляет конкретные реализацию зависящих интерфейсов.
     * @return объект реализующий интерфейс UserService
     * @throws ServiceCreatorException
     */
    public UserService getUserService() throws ServiceCreatorException {
        if (userService == null) {
            UserServiceImpl userServiceImpl = new UserServiceImpl();
            userService = userServiceImpl;
            userServiceImpl.setUserDao(getUserDao());
        }
        return userService;
    }

    private UserDao userDao = null;

    private UserDao getUserDao() throws ServiceCreatorException {
        if (userDao == null) {
            UserDaoImpl userDaoImpl = new UserDaoImpl();
            userDao = userDaoImpl;
            userDaoImpl.setConnection(getConnection());
        }
        return userDao;
    }

    private Connection connection = null;

    private Connection getConnection() throws ServiceCreatorException {
        if (connection == null) {
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (ConnectionPoolException e) {
                throw new ServiceCreatorException(e);
            }
        }
        return connection;
    }

    /**
     * Позволяет использовать объект этого класса в try with resources
     */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (Exception ex) {}
    }
}
