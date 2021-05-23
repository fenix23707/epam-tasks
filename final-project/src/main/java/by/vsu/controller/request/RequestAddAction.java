package by.vsu.controller.request;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Сохраняет в атрибуты сегодняшнюю дату и направляет на /request/add.jsp.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /request/add.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class RequestAddAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        req.setAttribute("today",dateFormat.format(new Date()));
        return null;
    }
}
