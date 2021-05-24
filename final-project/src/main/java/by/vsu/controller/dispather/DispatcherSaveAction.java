package by.vsu.controller.dispather;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Dispatcher;
import by.vsu.entities.Role;
import by.vsu.service.exception.LoginAlreadyExistException;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает данные от пользователя, проверяет их. Если данные корректны
 * создает объект класса Dispatcher и сохраняет его. Затем направляет на
 * /user/list.html
 * В противном случае сообщает пользователю об ошибке.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /dispatcher/save.html
 *
 * @author Kovzov Vladislav
 * @see Action
 * @see by.vsu.controller.ActionFactory
 */
public class DispatcherSaveAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        Long id = null;
        try {
            String idStr = req.getParameter("id");
            if (idStr != null) {
                id = Long.valueOf(idStr);
            }
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
        String msg = null;
        if (login != null && !login.isBlank()
                && password != null && !password.isBlank()
                && name != null && !name.isBlank()) {
            Dispatcher dispatcher = new Dispatcher();
            dispatcher.setId(id);
            dispatcher.setLogin(login);
            dispatcher.setPassword(password);
            dispatcher.setName(name);
            dispatcher.setRole(Role.DISPATCHER);
            try {
                getServiceFactory().getDispatcherService().save(dispatcher);
            } catch (LoginAlreadyExistException e) {
                msg = "Такой логин уже существует";
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            msg = "Данные введены не верно.";
        }
        return new Forward("/user/list.html", msg);
    }
}

