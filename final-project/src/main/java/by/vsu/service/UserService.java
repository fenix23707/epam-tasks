package by.vsu.service;

import by.vsu.entities.User;

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
     * Сохраняет или обновляет существующего пользователя в БД
     *
     * @param user пользовотель которого надо сохранить/обновить
     * @throws ServiceException
     */
    void save(User user) throws ServiceException;

    /**
     * Удаляет пользователя из БД
     * @param id пользвателя, которого надо удалить
     * @throws ServiceException
     */
    void delete(Long id ) throws ServiceException;
}
