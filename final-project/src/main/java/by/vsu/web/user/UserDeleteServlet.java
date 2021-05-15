package by.vsu.web.user;

import by.vsu.di.ServiceCreator;
import by.vsu.di.ServiceCreatorException;
import by.vsu.service.ServiceException;
import by.vsu.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UserDeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //TODO: сделать нормальную обработку id
        String id = req.getParameter("id");
        if (id != null) {
            try (ServiceCreator creator = new ServiceCreator()) {
                UserService userService = creator.getUserService();
                userService.delete(Long.parseLong(id));
            } catch (NumberFormatException | ServiceCreatorException | ServiceException e) {
                throw new ServletException(e);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/user/list.html");
    }
}
