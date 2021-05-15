package by.vsu.service;

import by.vsu.dao.DaoException;
import by.vsu.dao.UserDao;
import by.vsu.entities.User;

import java.util.List;

/**
 *
 *
 * @author Kovzov Vladislav
 */
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    /**
     * Позволяет выбрать какую реализацию интерфейса UserDao использовать
     *
     * @param userDao объект класса реализующий интерфейс UserDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    //TODO: не тестировал
    @Override
    public User findById(Long id) throws ServiceException {
        try {
            return userDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    //TODO: если передать пользовотеля с несуществующим id то он не изменяется в БД и нам уведомления не прихоодит нужно исправить это
    @Override
    public void save(User user) throws ServiceException {
        try {
            if (user.getId() != null) {

                userDao.update(user);

            } else {
                userDao.create(user);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Long id) throws ServiceException {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
