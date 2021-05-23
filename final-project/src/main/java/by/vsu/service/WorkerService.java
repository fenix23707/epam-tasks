package by.vsu.service;

import by.vsu.entities.Worker;
import by.vsu.service.exception.ServiceException;

import java.util.List;

/**
 * Предоставляет методы для работы с сущностью Worker
 *
 * @author Kovzov Vladislav
 */
public interface WorkerService {
    /**
     * Находит рабочего по его id. При этом инициализирует список рабочих
     * планов в которых состоит данный рабочий
     * @param id рабочего
     * @return обйект класса Worker или null, если не удалось найти рабочего с таким id
     * @throws ServiceException
     */
    Worker findById(Long id) throws ServiceException;

    /**
     * Находит всех рабочих у которых worker.active == active.
     * @param active статус акаунта
     * @return список рабочих у которых worker.active == active.
     * @throws ServiceException
     * @see by.vsu.entities.User
     */
    List<Worker> findAllByActive(boolean active) throws ServiceException;

    /**
     * Находит всех рабочих, которые числятся в рабочем плане с workPlanId.
     * @param workPlanId для которого нужен список рабочих
     * @return список рабочих, состоящие в рабочем плане с workPlanId
     * @throws ServiceException
     */
    List<Worker> findAllByWorkPlanId(Long workPlanId) throws ServiceException;

    /**
     * Сохраняет рабочего.
     * @param worker рабочий которого нужно сохранить
     * @throws ServiceException
     */
    void save(Worker worker) throws ServiceException;
}
