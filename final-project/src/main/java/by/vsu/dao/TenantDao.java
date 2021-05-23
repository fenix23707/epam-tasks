package by.vsu.dao;

import by.vsu.entities.Tenant;

import java.util.List;

/**
 * TenantDao предоставляет набор методов, которые позволяют работать
 * с конкретной сущностью в БД.
 *
 * @author Kovzov Vladislav
 */
public interface TenantDao extends Dao<Tenant> {
    /**
     * Читает из баззы данных всех жильцов.
     *
     * @return список всех жильцов в БД
     * @throws DaoException
     */
    List<Tenant> readAll() throws DaoException;

    /**
     * Проверяет создавал ли жилец хотя бы одну заявку.
     * @param id жильца
     * @return true если жилец с id создал хотя бы 1 заявку. В противном случае false.
     * @throws DaoException
     */
    boolean isTenantCreatedRequest(Long id) throws DaoException;
}
