package by.vsu.service;


import by.vsu.entities.WorkPlan;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 * Предоставляет методы для работы с сущностью WorkPlan
 *
 * @author Kovzov Vladislav
 */
public interface WorkPlanService {
    /**
     * Сохряняет workPlan а так же workPlan.request
     * @param workPlan то что нужно сохранить
     * @throws ServiceException
     * @see WorkPlan
     */
    void save(WorkPlan workPlan) throws ServiceException;

    /**
     * Находит workPlan по его id
     * @param id workPlan кторого надо найти
     * @return объект класса WorkPlan или null, если workPlan с таким id нету
     * @throws ServiceException
     */
    WorkPlan findById(Long id) throws ServiceException;

    /**
     * Находит все workPlan обработанные диспетчером с dispatcherId
     * @param dispatcherId для какого диспетчера нужен список
     * @return список заявок обработанных dispatcherId
     * @throws ServiceException
     */
    List<WorkPlan> findAllByDispatcherId(Long dispatcherId) throws ServiceException;
}
