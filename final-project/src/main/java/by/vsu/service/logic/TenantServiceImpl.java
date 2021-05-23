package by.vsu.service.logic;

import by.vsu.dao.DaoException;
import by.vsu.dao.TenantDao;
import by.vsu.dao.UserDao;
import by.vsu.entities.Role;
import by.vsu.entities.Tenant;
import by.vsu.entities.User;
import by.vsu.service.BaseService;
import by.vsu.service.TenantService;
import by.vsu.service.exception.ServiceException;
import by.vsu.service.exception.UserNotExistException;

import java.util.List;

/**
 * @author Kovzov Vladislav
 */
public class TenantServiceImpl extends BaseService implements TenantService {
    private TenantDao tenantDao;

    private UserDao userDao;

    /**
     * Позволяет выбрать какую реализацию интерфейса TenantDao использовать
     *
     * @param tenantDao объект класса реализующий интерфейс tenantDao
     */
    public void setTenantDao(TenantDao tenantDao) {
        this.tenantDao = tenantDao;
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
    public List<Tenant> findAll() throws ServiceException {
        try {
            return tenantDao.readAll();
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tenant findById(Long id) throws ServiceException {
        try {
            getTransaction().start();
            Tenant tenant = null;
            User user = userDao.readByIdAndRole(id, Role.TENANT);
            if (user != null) {
                tenant = tenantDao.read(id);
                tenant.setRole(Role.TENANT);
                tenant.setLogin(user.getLogin());
                tenant.setName(user.getName());
                tenant.setActive(user.isActive());
            }
            getTransaction().commit();
            return tenant;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {}
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try{
                getTransaction().rollback();
            } catch (ServiceException ex) {}
            throw e;
        }
    }

    @Override
    public void save(Tenant tenant) throws ServiceException {
        try {
            getTransaction().start();
            if (tenant.getId() != null) {
                User user = userDao.readByIdAndRole(tenant.getId(), Role.TENANT);
                if (user != null) {
                    user.setLogin(tenant.getLogin());
                    user.setPassword(tenant.getPassword());
                    user.setName(tenant.getName());
                    userDao.update(user);
                    tenantDao.update(tenant);
                } else {
                    throw new UserNotExistException();
                }
            } else {
                User user = new User();
                user.setLogin(tenant.getLogin());
                user.setPassword(tenant.getPassword());
                user.setName(tenant.getName());
                user.setRole(Role.TENANT);
                Long id = userDao.create(user);
                tenant.setId(id);
                tenantDao.create(tenant);
            }
            getTransaction().commit();
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {}
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {}
            throw e;
        }
    }
}
