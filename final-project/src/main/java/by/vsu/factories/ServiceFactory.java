package by.vsu.factories;

import by.vsu.dao.*;
import by.vsu.service.*;

import java.sql.Connection;

/**
 * Создает и инициализирует сервисы выбирая конкретные реализации для них.
 *
 * @author Kovzov Vladislav
 */
public interface ServiceFactory extends AutoCloseable{
    WorkPlanService getWorkPlanService() throws ServiceFactoryException;

    WorkerService getWorkerService() throws ServiceFactoryException;

    DispatcherService getDispatcherService() throws ServiceFactoryException;

    RequestService getRequestService() throws ServiceFactoryException;

    /**
     * Создает объект TenantService с заранее выбранной реализацией. А так же
     * добавляет конкретные реализацию зависящих интерфейсов.
     * @return объект реализующий интерфейс TenantService
     * @throws ServiceFactoryException
     */
    TenantService getTenantService() throws ServiceFactoryException;

    /**
     * Создает объект UserService с заранее выбранной реализацией. А так же
     * добавляет конкретные реализацию зависящих интерфейсов.
     * @return объект реализующий интерфейс UserService
     * @throws ServiceFactoryException
     */
    UserService getUserService() throws ServiceFactoryException;

    WorkPlanDao getWorkPlanDao() throws ServiceFactoryException;

    WorkerDao getWorkerDao() throws ServiceFactoryException;

    DispatcherDao getDispatcherDao() throws ServiceFactoryException;

    RequestDao getRequestDao() throws ServiceFactoryException;

    TenantDao getTenantDao() throws ServiceFactoryException;

    UserDao getUserDao() throws ServiceFactoryException;

    Transaction getTransaction() throws ServiceFactoryException;

    Connection getConnection() throws ServiceFactoryException;
}
