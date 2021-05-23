package by.vsu.dao;

import by.vsu.entities.Role;
import by.vsu.entities.User;

import java.util.List;

/**
 * UserDao предоставляет набор методов, которые позволяют работать
 * с конкретной сущностью в БД.
 *
 * @author Kovzov Vladislav
 */
public interface UserDao extends Dao<User> {
    /**
     * Возвращает список всех пользователей хранящихся в БД.
     *
     * @return список пользователей хранящихся в БД
     * @throws DaoException
     */
    List<User> readAll() throws DaoException;

    List<User> readAll(boolean active) throws DaoException;

    /**
     * Ищет пользавателя в БД с нужным login и password
     *
     * @param login
     * @param password
     * @return пользователя, или null, если нету пользовотеля в БД
     * с таким login и password
     * @throws DaoException
     */
    User readByLoginAndPassword(String login, String password) throws DaoException;

    User readByLoginAndPassword(String login, String password, boolean active) throws DaoException;

    /**
     * Ищет пользавателя в БД с нужным id и role
     * @param id
     * @param role цифра соответствующая роли
     * @return пользователя, или null, если нету пользовотеля в БД
     * с таким id и role
     */
    User readByIdAndRole(Long id, Role role) throws DaoException;

    User readByIdAndRole(Long id, Role role, boolean active) throws DaoException;

    /**
     * Изменяет статус аккаунта.
     * @param id статус какого аккаунта надо изменить.
     * @param active какой статус поставить
     * @throws DaoException
     */
    void updateActiveStatus(Long id, boolean active) throws DaoException;
}
