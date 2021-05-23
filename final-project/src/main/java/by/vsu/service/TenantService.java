package by.vsu.service;

import by.vsu.entities.Tenant;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 * Предоставляет методы для работы с Tenant
 *
 * @author Kovzov Vladislav
 */
public interface TenantService {
    /**
     * Получает список жильцов.
     *
     * @return список всех жильцов хранящихся в БД
     * @throws ServiceException
     */
    List<Tenant> findAll() throws ServiceException;

    /**
     * Находит жильца в БД по id
     * @param id id жильца, которого нужно найти в БД
     * @return Объект Tenant или null, если пользовотеля с таким id нету в БД
     * @throws ServiceException
     */
    Tenant findById(Long id) throws ServiceException;

    /**
     * Создает или обновляет жильца в БД
     * @param tenant объект который надо создать или обновить в БД
     * @throws ServiceException
     */
    void save(Tenant tenant) throws ServiceException;
}
