package by.vsu;

import by.vsu.dao.DaoException;
import by.vsu.dao.mysql.UserDaoImpl;
import by.vsu.di.ServiceCreator;
import by.vsu.di.ServiceCreatorException;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.pool.ConnectionPool;
import by.vsu.pool.ConnectionPoolException;
import by.vsu.service.ServiceException;
import by.vsu.service.UserService;

import java.lang.reflect.InvocationTargetException;
import java.security.Provider;
import java.sql.*;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ThreadLocalRandom;

public class Test {
    public static void main(String[] args) throws ConnectionPoolException, ServiceCreatorException, ServiceException {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.init(
                "com.mysql.cj.jdbc.Driver",
                "jdbc:mysql://localhost:3306/housing_and_utilities_db?useUnicode=true&serverTimezone=" + TimeZone.getDefault().getID(),
                "root",
                "root",
                5,
                10,
                0
        );
        /*Connection c = pool.getConnection();
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setConnection(c);
        User user1 = new User();
        user1.setId(2L);
        user1.setLogin("abs" + ThreadLocalRandom.current().nextInt());
        user1.setPassword("abs");
        user1.setRole(Role.DISPATCHER);
        userDao.create(user1);*/
        try(ServiceCreator serviceCreator = new ServiceCreator()) {
            User user1 = new User();
            user1.setId(2L);
            user1.setLogin("abs" + ThreadLocalRandom.current().nextInt());
            user1.setPassword("abs");
            user1.setRole(Role.ADMIN);

            UserService userService = serviceCreator.getUserService();
            userService.save(user1);
            List<User> users = userService.findAll();
            for (User user: users) {
                System.out.println(user);
            }
            System.out.println("OK");
        }
    }
}
