package by.vsu.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Очищает сессию и направляет пользователя на /index.html.
 * Объект этого класса создается когда пользователь переходит на старницу
 * с адресом: /logout.html
 *
 * @see ActionFactory
 * @see Action
 * @author Kovzov Vladislav
 */
public class LogoutAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return new Forward("/index.html");
    }
}
