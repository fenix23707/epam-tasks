package by.vsu.web.user;

import by.vsu.di.ServiceCreator;
import by.vsu.di.ServiceCreatorException;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.service.ServiceException;
import by.vsu.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Перенаправляет на edit.jsp.
 * Если был передан в качестве параметра id, то он его проверяет на валидность,
 * с точки зрения данных и пытается получить пользователя с этим id. Если данные
 * не валидны или пользователя не существует, то отправляет статус 404 и
 * перенаправления не просиходит.
 *
 */
public class UserEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try (ServiceCreator creator = new ServiceCreator()) {
                UserService userService = creator.getUserService();
                User user = userService.findById(Long.parseLong(id));
                if (user != null) {
                    req.setAttribute("user",user);
                } else {
                    throw new IllegalArgumentException();
                }

            } catch (ServiceCreatorException | ServiceException e) {
                throw new ServletException(e);
            } catch (IllegalArgumentException e) {
                resp.sendError(404);
                return;
            }
        }
        req.setAttribute("roles", Role.values());
        req.getRequestDispatcher("/WEB-INF/jsp/user/edit.jsp").forward(req,resp);
    }
}
