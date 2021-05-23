package by.vsu.controller;

import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.User;
import by.vsu.service.UserService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;


/**
 * Получает login и password от пользователя, проверяет их.
 * Затем пытается получить объект с таким login и password.
 * Если объект удалось получить, то создает сессию. И направляет его
 * на /index.html. В противном случае отображает login.jsp с
 * сообщением об ошибке. Объект этого класса создается когда пользователь
 * переходит на старницу с адресом: /login.html
 *
 * @see ActionFactory
 * @see Action
 * @author Kovzov Vladislav
 */
public class LoginAction extends Action{
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        if (login != null && !login.isBlank()
                && password != null && !password.isBlank()) {
            try {
                UserService userService = getServiceFactory().getUserService();
                User user = userService.login(login,password);
                if (user != null) {
                    HttpSession session = req.getSession();
                    session.setAttribute("session_user",user);
                    return new Forward("/index.html");
                } else {
                    String msg = "Не верный логин или пароль!";
                    return new Forward("/login.html", msg);
                }
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }
}
