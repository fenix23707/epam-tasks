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

public class UserSaveServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:  логин и пассворд могут прийти равные нулл и ид тоже самое надо обработать все варианты
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Role role = null;
        try {
            role = Role.valueOf(req.getParameter("role"));
        } catch (NullPointerException | IllegalArgumentException ex) {}

        if (login != null && !login.isBlank() &&
                password != null && !password.isBlank() ) {
            Long id = null;
            try {
                id = Long.parseLong(req.getParameter("id"));
            } catch (NumberFormatException e) {}
            User user = new User();
            user.setId(id);
            user.setLogin(login);
            user.setPassword(password);
            user.setRole(role);
            try(ServiceCreator creator = new ServiceCreator()) {
                UserService userService = creator.getUserService();
                userService.save(user);
            } catch (ServiceCreatorException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/user/list.html");
    }
}
