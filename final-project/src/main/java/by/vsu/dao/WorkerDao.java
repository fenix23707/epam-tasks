package by.vsu.dao;

import by.vsu.entities.WorkPlan;
import by.vsu.entities.Worker;

import java.util.List;

/**
 * WorkerDao предоставляет набор методов, которые позволяют работать
 * с конкретной сущностью в БД.
 *
 * @author Kovzov Vladislav
 */
public interface WorkerDao extends Dao<Worker> {
    /**
     * Читает всех работников из БД.
     * @return список работников.
     * @throws DaoException
     */
    List<Worker> readAll() throws DaoException;

    /**
     * Находит список workPlan для одного работника.
     * @param workerId для какого работника нужен список
     * @return
     * @throws DaoException
     */
    List<WorkPlan> readWorkPlansByWorkerId(Long workerId) throws DaoException;

    /**
     * Проверяет состоит ли работник хотя бы в одном workPlan.
     * @param id работника
     * @return true если работник с id состоит хотя бы в 1 workPlan. В противном случае false.
     * @throws DaoException
     */
    boolean isWorkerExistInWorkPlan(Long id) throws DaoException;
}
