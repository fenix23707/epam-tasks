package by.vsu.service.logic;

import by.vsu.dao.*;
import by.vsu.entities.*;
import by.vsu.service.BaseService;
import by.vsu.service.WorkerService;
import by.vsu.service.exception.ServiceException;
import by.vsu.service.exception.UserNotExistException;

import java.util.ArrayList;
import java.util.List;

public class WorkerServiceImpl extends BaseService implements WorkerService {
    private WorkerDao workerDao;

    private UserDao userDao;

    private WorkPlanDao workPlanDao;

    private RequestDao requestDao;

    /**
     * Позволяет выбрать какую реализацию интерфейса WorkerDao использовать
     *
     * @param workerDao объект класса реализующий интерфейс WorkerDao
     */
    public void setWorkerDao(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    /**
     * Позволяет выбрать какую реализацию интерфейса UserDao использовать
     *
     * @param userDao объект класса реализующий интерфейс tenantDao
     */
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setWorkPlanDao(WorkPlanDao workPlanDao) {
        this.workPlanDao = workPlanDao;
    }

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public Worker findById(Long id) throws ServiceException {
        try {
            getTransaction().start();
            Worker worker = null;
            User user = userDao.readByIdAndRole(id, Role.WORKER);
            if (user != null) {
                worker = workerDao.read(id);
                worker.setRole(Role.WORKER);
                worker.setLogin(user.getLogin());
                worker.setName(user.getName());
                worker.setActive(user.isActive());

                for (WorkPlan item : worker.getWorkPlans()) {
                    WorkPlan workPlan = workPlanDao.read(item.getId());
                    item.setEndDay(workPlan.getEndDay());
                    item.setWorkScope(workPlan.getWorkScope());
                    Request request = requestDao.read(workPlan.getRequest().getId());
                    item.setRequest(request);
                    item.setDispatcher(workPlan.getDispatcher());
                    item.setWorkers(workPlan.getWorkers());
                }
            }
            getTransaction().commit();
            return worker;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw e;
        }
    }

    @Override
    public List<Worker> findAllByActive(boolean active) throws ServiceException {
        try {
            getTransaction().start();
            List<Worker> workers = workerDao.readAll();
            List<Worker> neededWorkers = new ArrayList<>();
            for (Worker worker : workers) {
                User user = userDao.read(worker.getId());
                if (user.isActive() == active) {
                    worker.setName(user.getName());
                    worker.setLogin(user.getLogin());
                    neededWorkers.add(worker);
                }
            }
            getTransaction().commit();
            return neededWorkers;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw e;
        }
    }

    @Override
    public List<Worker> findAllByWorkPlanId(Long workPlanId) throws ServiceException {
        try {
            getTransaction().start();
            List<Worker> workers = workPlanDao.readWorkersByWorkPlanId(workPlanId);
            for (Worker item : workers) {
                User user = userDao.read(item.getId());
                item.setLogin(user.getLogin());
                item.setName(user.getName());
                item.setActive(user.isActive());

                Worker worker = workerDao.read(item.getId());
                item.setSpecialization(worker.getSpecialization());
            }
            getTransaction().commit();
            return workers;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw e;
        }
    }

    @Override
    public void save(Worker worker) throws ServiceException {
        try {
            getTransaction().start();
            if (worker.getId() != null) {
                User user = userDao.readByIdAndRole(worker.getId(), Role.WORKER);
                if (user != null) {
                    user.setLogin(worker.getLogin());
                    user.setPassword(worker.getPassword());
                    user.setName(worker.getName());
                    userDao.update(user);
                    workerDao.update(worker);
                } else {
                    throw new UserNotExistException();
                }
            } else {
                User user = new User();
                user.setLogin(worker.getLogin());
                user.setPassword(worker.getPassword());
                user.setName(worker.getName());
                user.setRole(Role.WORKER);
                Long id = userDao.create(user);
                worker.setId(id);
                workerDao.create(worker);
            }
            getTransaction().commit();
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) {
            }
            throw e;
        }
    }
}
