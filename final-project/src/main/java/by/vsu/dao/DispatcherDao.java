package by.vsu.dao;

import by.vsu.entities.Dispatcher;

/**
 * DispatcherDao предоставляет набор методов, которые позволяют работать
 * с конкретной сущностью в БД.
 *
 * @author Kovzov Vladislav
 */
public interface DispatcherDao extends Dao<Dispatcher> {
    /**
     * Проверяет обработал ли диспетчер хотя бы одну заявку.
     * @param id диспетчера
     * @return true если диспетчер с id обработал хотя бы 1 заявку. В противном случае false.
     * @throws DaoException
     */
    boolean isDispatcherCreatedWorkPlan(Long id) throws DaoException;
}
