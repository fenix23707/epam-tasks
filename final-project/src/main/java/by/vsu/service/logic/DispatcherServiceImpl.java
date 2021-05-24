package by.vsu.service.logic;

import by.vsu.dao.DaoException;
import by.vsu.dao.DispatcherDao;
import by.vsu.dao.UserDao;
import by.vsu.entities.Dispatcher;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.service.BaseService;
import by.vsu.service.DispatcherService;
import by.vsu.service.exception.LoginAlreadyExistException;
import by.vsu.service.exception.ServiceException;
import by.vsu.service.exception.UserNotExistException;

import java.util.List;

public class DispatcherServiceImpl extends BaseService implements DispatcherService {
    private DispatcherDao dispatcherDao;

    private UserDao userDao;

    /**
     * Позволяет выбрать какую реализацию интерфейса DispatcherDao использовать
     *
     * @param dispatcherDao объект класса реализующий интерфейс dispatcherDao
     */
    public void setDispatcherDao(DispatcherDao dispatcherDao) {
        this.dispatcherDao = dispatcherDao;
    }

    /**
     * Позволяет выбрать какую реализацию интерфейса UserDao использовать
     *
     * @param userDao объект класса реализующий интерфейс tenantDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Dispatcher findById(Long id) throws ServiceException {
        try {
            getTransaction().start();
            Dispatcher dispatcher = null;
            User user = userDao.readByIdAndRole(id, Role.DISPATCHER);
            if (user != null) {
                dispatcher = dispatcherDao.read(id);
                dispatcher.setLogin(user.getLogin());
                dispatcher.setPassword(user.getPassword());
                dispatcher.setName(user.getName());
                dispatcher.setRole(Role.DISPATCHER);
                dispatcher.setActive(user.isActive());
            }
            getTransaction().commit();
            return dispatcher;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw e;
        }
    }

    @Override
    public List<Dispatcher> findAll() throws ServiceException {
        throw new UserNotExistException();
    }

    @Override
    public void save(Dispatcher dispatcher) throws ServiceException {
        try {
            getTransaction().start();
            if (userDao.isLoginAlreadyExist(dispatcher.getLogin())) {
                try {
                    getTransaction().commit();
                } catch (ServiceException ex) { }
                throw new LoginAlreadyExistException();
            }
            if (dispatcher.getId() != null) {
                User user = userDao.readByIdAndRole(dispatcher.getId(),
                        Role.DISPATCHER);
                if (user != null) {
                    user.setLogin(dispatcher.getLogin());
                    user.setPassword(dispatcher.getPassword());
                    user.setName(dispatcher.getName());
                    userDao.update(user);
                    dispatcherDao.update(dispatcher);
                } else {
                    throw new UserNotExistException();
                }
            } else {
                User user = new User();
                user.setLogin(dispatcher.getLogin());
                user.setPassword(dispatcher.getPassword());
                user.setName(dispatcher.getName());
                user.setRole(Role.DISPATCHER);

                Long id = userDao.create(user);
                dispatcher.setId(id);
                dispatcherDao.create(dispatcher);
            }
            getTransaction().commit();
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw e;
        }

    }
}
