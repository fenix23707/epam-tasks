package by.vsu.controller.request;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Request;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает id заявки находит ее и сохраняет в атрибуты. Затем направляет
 * на /request/info.jsp.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /request/info.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class RequestInfoAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestService requestService  = getServiceFactory().getRequestService();

            Request request = requestService.findById(Long.valueOf(
                    req.getParameter("request_id")));
            req.setAttribute("request",request);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
        return null;
    }
}
