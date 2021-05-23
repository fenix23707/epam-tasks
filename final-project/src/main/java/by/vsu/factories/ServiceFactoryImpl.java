package by.vsu.factories;

import by.vsu.dao.*;
import by.vsu.dao.mysql.*;
import by.vsu.pool.ConnectionPool;
import by.vsu.pool.ConnectionPoolException;
import by.vsu.service.*;
import by.vsu.service.logic.*;

import java.sql.Connection;

/**
 * Реализует интерфейс ServiceFactory
 *
 * @author kovzov Vladislav
 */
public class ServiceFactoryImpl implements ServiceFactory{
    private WorkPlanService workPlanService = null;

    private WorkerService workerService = null;

    private DispatcherService dispatcherService = null;

    private RequestService requestService = null;

    private TenantService tenantService = null;

    private UserService userService = null;

    private WorkPlanDao workPlanDao = null;

    private WorkerDao workerDao = null;

    private DispatcherDao dispatcherDao = null;

    private RequestDao requestDao = null;

    private TenantDao tenantDao = null;

    private UserDao userDao = null;

    private Transaction transaction = null;

    private Connection connection = null;

    @Override
    public WorkPlanService getWorkPlanService() throws ServiceFactoryException {
        if (workPlanService == null) {
            WorkPlanServiceImpl workPlanServiceImpl = new WorkPlanServiceImpl();
            workPlanService = workPlanServiceImpl;
            workPlanServiceImpl.setTransaction(getTransaction());
            workPlanServiceImpl.setWorkPlanDao(getWorkPlanDao());
            workPlanServiceImpl.setRequestDao(getRequestDao());
        }
        return workPlanService;
    }

    @Override
    public WorkerService getWorkerService() throws ServiceFactoryException {
        if (workerService == null) {
            WorkerServiceImpl workerServiceImpl = new WorkerServiceImpl();
            workerService = workerServiceImpl;
            workerServiceImpl.setTransaction(getTransaction());
            workerServiceImpl.setUserDao(getUserDao());
            workerServiceImpl.setWorkerDao(getWorkerDao());
            workerServiceImpl.setWorkPlanDao(getWorkPlanDao());
            workerServiceImpl.setRequestDao(getRequestDao());
        }
        return workerService;
    }

    @Override
    public DispatcherService getDispatcherService() throws ServiceFactoryException {
        if (dispatcherService == null) {
            DispatcherServiceImpl dispatcherServiceImpl = new DispatcherServiceImpl();
            dispatcherService = dispatcherServiceImpl;
            dispatcherServiceImpl.setTransaction(getTransaction());
            dispatcherServiceImpl.setUserDao(getUserDao());
            dispatcherServiceImpl.setDispatcherDao(getDispatcherDao());
        }
        return dispatcherService;
    }

    @Override
    public RequestService getRequestService() throws ServiceFactoryException {
        if (requestService == null) {
            RequestServiceImpl requestServiceImpl = new RequestServiceImpl();
            requestService = requestServiceImpl;
            requestServiceImpl.setRequestDao(getRequestDao());
            requestServiceImpl.setTransaction(getTransaction());
        }
        return requestService;
    }

    @Override
    public TenantService getTenantService() throws ServiceFactoryException {
        if (tenantService == null) {
            TenantServiceImpl tenantServiceImpl = new TenantServiceImpl();
            tenantService = tenantServiceImpl;
            tenantServiceImpl.setTenantDao(getTenantDao());
            tenantServiceImpl.setUserDao(getUserDao());
            tenantServiceImpl.setTransaction(getTransaction());
        }
        return tenantService;
    }

    @Override
    public UserService getUserService() throws ServiceFactoryException {
        if (userService == null) {
            UserServiceImpl userServiceImpl = new UserServiceImpl();
            userService = userServiceImpl;
            userServiceImpl.setTransaction(getTransaction());
            userServiceImpl.setUserDao(getUserDao());
            userServiceImpl.setTenantDao(getTenantDao());
            userServiceImpl.setDispatcherDao(getDispatcherDao());
            userServiceImpl.setWorkerDao(getWorkerDao());
        }
        return userService;
    }

    @Override
    public WorkPlanDao getWorkPlanDao() throws ServiceFactoryException {
        if (workPlanDao == null) {
            WorkPlanDaoImpl workPlanDaoImpl = new WorkPlanDaoImpl();
            workPlanDao = workPlanDaoImpl;
            workPlanDaoImpl.setConnection(getConnection());
        }
        return workPlanDao;
    }

    @Override
    public WorkerDao getWorkerDao() throws ServiceFactoryException {
        if (workerDao == null) {
            WorkerDaoImp workerDaoImp = new WorkerDaoImp();
            workerDao = workerDaoImp;
            workerDaoImp.setConnection(getConnection());
        }
        return workerDao;
    }

    @Override
    public DispatcherDao getDispatcherDao() throws ServiceFactoryException {
        if (dispatcherDao == null) {
            DispatcherDaoImpl dispatcherDaoImpl = new DispatcherDaoImpl();
            dispatcherDao = dispatcherDaoImpl;
            dispatcherDaoImpl.setConnection(getConnection());
        }
        return dispatcherDao;
    }

    @Override
    public RequestDao getRequestDao() throws ServiceFactoryException {
        if (requestDao == null) {
            RequestDaoImpl requestDaoImpl = new RequestDaoImpl();
            requestDao = requestDaoImpl;
            requestDaoImpl.setConnection(getConnection());
        }
        return requestDao;
    }

    @Override
    public TenantDao getTenantDao() throws ServiceFactoryException {
        if (tenantDao == null) {
            TenantDaoImpl tenantDaoImpl = new TenantDaoImpl();
            //TODO: написать почему следующие две строчки должны идти в таком порядке
            tenantDao = tenantDaoImpl;
            tenantDaoImpl.setConnection(getConnection());
        }
        return tenantDao;
    }

    @Override
    public UserDao getUserDao() throws ServiceFactoryException {
        if (userDao == null) {
            UserDaoImpl userDaoImpl = new UserDaoImpl();
            userDao = userDaoImpl;
            userDaoImpl.setConnection(getConnection());
        }
        return userDao;
    }

    @Override
    public Transaction getTransaction() throws ServiceFactoryException {
        if (transaction == null) {
            TransactionImpl transactionImpl = new TransactionImpl();
            transaction = transactionImpl;
            transactionImpl.setConnection(getConnection());
        }
        return transaction;
    }

    @Override
    public Connection getConnection() throws ServiceFactoryException {
        if (connection == null) {
            try {
                connection = ConnectionPool.getInstance().getConnection();
            } catch (ConnectionPoolException e) {
                throw new ServiceFactoryException(e);
            }
        }
        return connection;
    }

    /* Позволяет использовать объект этого класса в try with resources */
    @Override
    public void close() {
        try {
            connection.close();
        } catch (Exception ex) { }
    }
}
