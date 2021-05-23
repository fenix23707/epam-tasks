package by.vsu.controller.request;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Request;
import by.vsu.entities.User;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Получает из сесси объект User, затем по user.id находит все заявки
 * сделанные им и сохраняет в атрибуты. Затем направляет на /request/list.jsp.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /request/list.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class RequestListAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestService requestService  = getServiceFactory().getRequestService();
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("session_user");
            List<Request> requests = requestService.findAllByTenantId(user.getId());
            req.setAttribute("requests",requests);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return null;
    }
}
