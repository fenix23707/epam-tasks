package by.vsu.controller.dispather;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Dispatcher;
import by.vsu.service.DispatcherService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Направляет на /dispatcher/edit.jsp и если был передан id, то находит
 * объект Dispatcher и сохраняет его в атрибуты.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /dispatcher/save.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class DispatcherEditAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                DispatcherService dispatcherService = getServiceFactory().getDispatcherService();
                Dispatcher dispatcher = dispatcherService.findById(Long.parseLong(id));
                if (dispatcher != null) {
                    req.setAttribute("dispatcher", dispatcher);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (IllegalArgumentException e) {
                return new Forward(404);
            }
        }
        return null;
    }
}
