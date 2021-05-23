package by.vsu.dao;

import by.vsu.entities.WorkPlan;
import by.vsu.entities.Worker;

import java.util.List;

/**
 * WorkPlanDao предоставляет набор методов, которые позволяют работать
 * с конкретной сущностью в БД.
 *
 * @author Kovzov Vladislav
 */
public interface WorkPlanDao extends Dao<WorkPlan> {
    /**
     * Находит все workPlan обработанные диспетчером с id
     * @param dispatcherId планы какого диспетчера нужно найти
     * @return список планов обработанные диспетчером с dispatcherId
     * @throws DaoException
     */
    List<WorkPlan> readAllByDispatcherId(Long dispatcherId) throws DaoException;

    /**
     * Находит всех рабочих входящих в workPlan с ид == workPlanId
     * @param workPlanId список рабочиз для какого плана надо найти
     * @return список рабочих для плана с ид == workPlanId
     * @throws DaoException
     */
    List<Worker> readWorkersByWorkPlanId(Long workPlanId) throws DaoException;
}

