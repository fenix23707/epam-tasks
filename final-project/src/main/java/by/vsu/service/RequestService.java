package by.vsu.service;

import by.vsu.entities.Request;
import by.vsu.entities.RequestStatus;
import by.vsu.entities.Tenant;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 * Предоставляет методы для работы с сущностью Request
 *
 * @author Kovzov Vladislav
 */
public interface RequestService {
    /**
     * Находит все заявки жильца в БД по его id.
     *
     * @param tenantId
     * @return список заявок жильца с ид tenantId
     * @throws ServiceException
     */
    List<Request> findAllByTenantId(Long tenantId) throws ServiceException;

    /**
     * Находит все заяки жельца по статусу
     * @param status с каким статусом список заявок нужен
     * @return список заявок у которых статус == status
     * @throws ServiceException
     */
    List<Request> findAllByRequestStatus(RequestStatus status) throws ServiceException;

    /**
     * Находит зявку с указанным id
     * @param id заявки, которую надо найти
     * @return заявка с указанным id или null, если такой заявки нету
     * @throws ServiceException
     */
    Request findById(Long id) throws ServiceException;

    /**
     * Создает или обновляет заявку в БД
     *
     * @param request заявка которую надо создать/обновить
     * @throws ServiceException
     */
    void save(Request request) throws ServiceException;

    /**
     * Обновляет статус заявки
     * @param id заявки, статус который надо изменить
     * @param status новый статус заявки
     * @throws ServiceException
     */
    void updateStatusById(Long id, RequestStatus status) throws ServiceException;

    /**
     * Удаляет заявку в соответствие с переданным id
     * @param id заявки которой надо удалить.
     * @return true, если такая заявка существовала и она удалилась, false если такой заявки не было
     * @throws ServiceException
     */
    boolean delete(Long id) throws ServiceException;
}
