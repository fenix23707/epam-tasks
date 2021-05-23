package by.vsu.service;

import by.vsu.entities.User;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 * Предоставляет методы для работы с сущностью User
 *
 * @author Kovzov Vladislav
 */
public interface UserService {
    /**
     * Получает список всех пользователей из БД
     *
     * @return список всех пользователей в БД
     * @throws ServiceException
     */
    List<User> findAll() throws ServiceException;

    /**
     * Находит пользовотеля в БД по id
     * @param id id пользовотеля, которого нужно найти в БД
     * @return Объект User или null, если пользовотеля с таким id нету в БД
     * @throws ServiceException
     */
    User findById(Long id) throws ServiceException;

    /**
     * По пееданным login и password получает пользователя
     * @param login
     * @param password
     * @return пользователя или null, если нету пользовотеля с таким
     * login и password
     * @throws ServiceException
     */
    User login(String login, String password) throws ServiceException;

    /**
     * Создает или обновляет существующего пользователя в БД
     *
     * @param user пользовотель которого надо создать/обновить
     * @throws ServiceException
     */
    void save(User user) throws ServiceException;

    /**
     * Удаляет пользователя из БД
     * @param id пользвателя, которого надо удалить
     * @return true, если объект с id был удален
     * @throws ServiceException
     */
    boolean delete(Long id ) throws ServiceException;
}
