package by.vsu.controller.user;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.User;
import by.vsu.service.UserService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Направляет на /user/list.jsp. Находит список всех активных пользователей
 * и сохраняет его в атрибуты.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /user/list.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class UserListAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UserService userService = getServiceFactory().getUserService();
            List<User> users = userService.findAll();
            req.setAttribute("users", users);
            return null;
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }
    }
}
