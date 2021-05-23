package by.vsu.controller.workplan;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Request;
import by.vsu.entities.RequestStatus;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Подготавляивает список запросов для workplan/create.jsp.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /workplan/create.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class WorkPlanCreateAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestService requestService  = getServiceFactory().getRequestService();
            List<Request> requests = requestService.findAllByRequestStatus(RequestStatus.CREATED);
            req.setAttribute("requests",requests);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return null;
    }
}
