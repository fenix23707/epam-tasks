package by.vsu.service.logic;

import by.vsu.dao.*;
import by.vsu.entities.*;
import by.vsu.service.BaseService;
import by.vsu.service.WorkPlanService;
import by.vsu.service.exception.ServiceException;

import java.util.List;

public class WorkPlanServiceImpl extends BaseService implements WorkPlanService {
    private WorkPlanDao workPlanDao;

    private RequestDao requestDao;

    public void setWorkPlanDao(WorkPlanDao workPlanDao) {
        this.workPlanDao = workPlanDao;
    }

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public WorkPlan findById(Long id) throws ServiceException {
        try {
            getTransaction().start();
            WorkPlan workPlan = workPlanDao.read(id);
            getTransaction().commit();
            return workPlan;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) { }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) { }
            throw e;
        }
    }

    @Override
    public List<WorkPlan> findAllByDispatcherId(Long dispatcherId) throws ServiceException {
        try {
            getTransaction().start();
            List<WorkPlan> workPlans = workPlanDao.readAllByDispatcherId(dispatcherId);
            for (WorkPlan workPlan: workPlans) {
                workPlan.setRequest(requestDao.read(workPlan.getRequest().getId()));
            }
            getTransaction().commit();
            return workPlans;
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) { }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) { }
            throw e;
        }
    }

    @Override
    public void save(WorkPlan workPlan) throws ServiceException {
        try {
            getTransaction().start();
            if (workPlan.getId() != null) {
                workPlanDao.update(workPlan);
            } else {
                workPlanDao.create(workPlan);
            }
            requestDao.update(workPlan.getRequest());
            getTransaction().commit();
        } catch (DaoException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) { }
            throw new ServiceException(e);
        } catch (ServiceException e) {
            try {
                getTransaction().rollback();
            } catch (ServiceException ex) { }
            throw e;
        }
    }
}
