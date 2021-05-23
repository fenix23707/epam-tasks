package by.vsu.dao;

import by.vsu.entities.Entity;

/**
 * Dao это основной интерфейс, который описывает базовые методы
 * для работы с БД.
 *
 * @param <T> класс-сущность, который обрабатывается этим интерфейсом
 *
 * @author Kovzov Vladislav
 */
public interface Dao <T extends Entity>{
    /**
     * Добавляет новый объект в БД
     *
     * @param entity объект, который надо добавить в БД
     * @return первичный ключ (В нашем случае id)
     * @throws DaoException
     */
    long create(T entity) throws DaoException;

    /**
     * Получает объект из БД по первичному ключу.
     *
     * @param id первичный ключ
     * @return объект прочитанный из БД
     * @throws DaoException
     */
    T read(Long id) throws  DaoException;

    /**
     * Обновляет данные по переданному объекту в БД
     *
     * @param entity объект данные которого надо обновить в БД
     * @return true если картеж с id обновлен в БД
     * @throws DaoException
     */
    void update(T entity) throws DaoException;

    /**
     * Удаляет объект по первичному ключу из БД.
     *
     * @param id первичный ключ объекта, который надо удалить из БД
     * @return true если картеж с переданным id удален из БД
     * @throws DaoException
     */
    boolean delete(Long id) throws DaoException;
}
