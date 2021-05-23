package by.vsu.controller.request;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает id заявки и удаляет ее. Затем направляет
 * на /workplan/create.html с сообщщением оь результате.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /request/delete.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class RequestDeleteAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestService requestService = getServiceFactory().getRequestService();
            String msg;
            if (requestService.delete(Long.valueOf(req.getParameter("request_id")))) {
                msg = "Заявка успешно удалена";
            } else {
                msg = "Не удалось удалить заявку";
            }
            return new Forward("/workplan/create.html", msg);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
    }
}
