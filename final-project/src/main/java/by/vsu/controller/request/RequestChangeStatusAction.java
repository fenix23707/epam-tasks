package by.vsu.controller.request;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.RequestStatus;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Получает: id заявки, объект User из сессии. Затем:
 *  - если user.role ==  WORKER, то устанавливает статус заявки COMPLETED
 *  и направляет на /workplan/list.html
 *  - елси user.role == TENANT, то устанавливает статус заявки CONFIRMED
 *  и направляет на /request/list.html
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /request/change_status.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class RequestChangeStatusAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Long id = Long.valueOf(req.getParameter("request_id"));
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("session_user");
            RequestService requestService = getServiceFactory().getRequestService();
            if (user.getRole() == Role.WORKER) {
                requestService.updateStatusById(id, RequestStatus.COMPLETED);
                return new Forward("/workplan/list.html");
            } else if (user.getRole() == Role.TENANT) {
                requestService.updateStatusById(id, RequestStatus.CONFIRMED);
                return new Forward("/request/list.html");
            }
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
        return new Forward("/login.html", "доступ запрещен");
    }
}
