package by.vsu.dao;

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
}
