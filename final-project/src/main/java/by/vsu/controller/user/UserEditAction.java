package by.vsu.controller.user;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.factories.ServiceFactoryImpl;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.service.UserService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает id находит пользователя с таким id. Затем в зависимости
 * от роли пользователя перенаправляет его на:
 *  - /tenant/edit.html
 *  - /dispatcher/edit.html
 *  - /worker/edit.html
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /user/edit.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class UserEditAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        Role role;
        if (id != null) {
            try (ServiceFactoryImpl creator = new ServiceFactoryImpl()) {
                UserService userService = creator.getUserService();
                User user = userService.findById(Long.parseLong(id));
                if (user != null) {
                    req.setAttribute("id", user.getId());
                    role = user.getRole();
                } else {
                    throw new IllegalArgumentException();
                }

            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (IllegalArgumentException e) {
                return new Forward(404);
            }
        } else {
            role = Role.valueOf(req.getParameter("role"));
        }
        switch (role) {
            case TENANT:
                return new Forward("/tenant/edit.html", false);
            case DISPATCHER:
                return new Forward("/dispatcher/edit.html", false);
            case WORKER:
                return new Forward("/worker/edit.html", false);
            default:
                return new Forward(404);
        }
    }
}
