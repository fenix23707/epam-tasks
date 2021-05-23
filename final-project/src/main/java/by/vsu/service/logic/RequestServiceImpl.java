package by.vsu.service.logic;

import by.vsu.dao.DaoException;
import by.vsu.dao.RequestDao;
import by.vsu.entities.Request;
import by.vsu.entities.RequestStatus;
import by.vsu.service.BaseService;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;

import java.util.List;

public class RequestServiceImpl extends BaseService implements RequestService {
    private RequestDao requestDao;

    public void setRequestDao(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    @Override
    public List<Request> findAllByTenantId(Long tenantId) throws ServiceException {
        try {
            return requestDao.readAllByTenantId(tenantId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Request> findAllByRequestStatus(RequestStatus status) throws ServiceException {
        try {
            return requestDao.readAllByRequestStatus(status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Request findById(Long id) throws ServiceException {
        try {
            return requestDao.read(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void save(Request request) throws ServiceException {
        try {
            if (request.getId() != null) {
                requestDao.update(request);
            } else {
                request.setId(requestDao.create(request));
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateStatusById(Long id, RequestStatus status) throws ServiceException {
        try {
            requestDao.updateStatusById(id, status);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        try {
            return requestDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }
}
