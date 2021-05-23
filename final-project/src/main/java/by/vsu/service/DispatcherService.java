package by.vsu.service;

import by.vsu.entities.Dispatcher;
import by.vsu.entities.Role;
import by.vsu.entities.Tenant;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 * Предоставляет методы для работы с сущностью Dispatcher
 *
 * @author Kovzov Vladislav
 */
public interface DispatcherService {

    /**
     * Находит диспетчера в БД по id
     *
     * @param id id диспетчера, которого надо найти
     * @return Объект Tenant или null, если пользовотеля с таким id нету в БД
     * @throws ServiceException
     */
    Dispatcher findById(Long id) throws ServiceException;

    /**
     * Получает список диспетчеров.
     *
     * @return список всех диспетчеров хранящихся в БД
     * @throws ServiceException
     */
    List<Dispatcher> findAll() throws ServiceException;

    /**
     * Создает или обновляет диспетчера в БД
     * @param dispatcher объект который надо создать или обновить в БД
     * @throws ServiceException
     */
    void save(Dispatcher dispatcher) throws ServiceException;
}
