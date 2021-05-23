package by.vsu.service.logic;

import by.vsu.dao.*;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.service.BaseService;
import by.vsu.service.UserService;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 *
 *
 * @author Kovzov Vladislav
 */
public class UserServiceImpl extends BaseService implements UserService {
    private UserDao userDao;

    private TenantDao tenantDao;

    private WorkerDao workerDao;

    private DispatcherDao dispatcherDao;

    /**
     * Позволяет выбрать какую реализацию интерфейса UserDao использовать
     *
     * @param userDao объект класса реализующий интерфейс UserDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setTenantDao(TenantDao tenantDao) {
        this.tenantDao = tenantDao;
    }

    public void setWorkerDao(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    public void setDispatcherDao(DispatcherDao dispatcherDao) {
        this.dispatcherDao = dispatcherDao;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        try {
            return userDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User findById(Long id) throws ServiceException {
        try {
            return userDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String login, String password) throws ServiceException {
        try {
            return userDao.readByLoginAndPassword(login,password, true);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

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
    public boolean delete(Long id) throws ServiceException {
        try {
            User user = userDao.read(id);

            if (canDelete(user)) {
                return userDao.delete(id);
            } else {
                user.setActive(false);
                userDao.updateActiveStatus(user.getId(), false);
                return true;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private boolean canDelete(User user) throws DaoException {
        if (!user.isActive()) {
            return false;
        } else if (user.getRole() == Role.TENANT) {
            return  !tenantDao.isTenantCreatedRequest(user.getId());
        } else if (user.getRole() == Role.DISPATCHER) {
            return !dispatcherDao.isDispatcherCreatedWorkPlan(user.getId());
        } else if (user.getRole() == Role.WORKER) {
            return !workerDao.isWorkerExistInWorkPlan(user.getId());
        }
        return false;
    }
}
