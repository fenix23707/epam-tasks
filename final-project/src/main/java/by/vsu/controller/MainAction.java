package by.vsu.controller;

import by.vsu.entities.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Определяет роль пользователя и решает на какую страницу его направить.
 * Объект этого класса создается когда пользователь переходит на старницу
 * с адресом: /index.html или /
 *
 * @see ActionFactory
 * @see Action
 * @author Kovzov Vladislav
 */
public class MainAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("session_user");
            if (user != null) {
                switch (user.getRole()) {
                    case ADMIN:
                        return new Forward("/user/list.html");
                    case TENANT:
                        return new Forward("/request/list.html");
                    case DISPATCHER:
                    case WORKER:
                        return new Forward("/workplan/list.html");

                    default:
                        String msg = String.format("Нет доступа для роли: %s",
                                user.getRole().getName());
                        return new Forward("/login.html", msg);
                }
            }
        }
        return new Forward("/login.html");
    }

}
