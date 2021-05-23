package by.vsu.dao;

import by.vsu.entities.Request;
import by.vsu.entities.RequestStatus;

import java.util.List;

/**
 * RequestDao предоставляет набор методов, которые позволяют работать
 * с конкретной сущностью в БД.
 *
 * @author Kovzov Vladislav
 */
public interface RequestDao extends Dao<Request>{
    /**
     * Читает из БД все заявки сделанные одним жильцом.
     *
     * @param tenantId ид жильца
     * @return список заявок сделанных одним жильцом
     * @throws DaoException
     */
    List<Request> readAllByTenantId(Long tenantId) throws DaoException;

    /**
     * Читает из БД все заявки, с переданным статусом.
     * @param status для которого нужен список заявок
     * @return список заявок со статусом == status
     * @throws DaoException
     */
    List<Request> readAllByRequestStatus(RequestStatus status) throws DaoException;

    /**
     * Обновляет статус заявки в БД.
     * @param id заявки, статус который надо изменить
     * @param status новый статус заявки
     * @throws DaoException
     */
    void updateStatusById(Long id, RequestStatus status) throws DaoException;

}
