package by.vsu.web.user;

import by.vsu.di.ServiceCreator;
import by.vsu.di.ServiceCreatorException;
import by.vsu.entities.Role;
import by.vsu.entities.User;
import by.vsu.pool.ConnectionPool;
import by.vsu.pool.ConnectionPoolException;
import by.vsu.service.ServiceException;
import by.vsu.service.UserService;
import by.vsu.service.UserServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.List;

/**
 * Подготовливает список пользователей и передает его list.jsp для отображения.
 *
 * @author Kovzov Vladislav
 */
public class UserListServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {

        String driverClass = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/housing_and_utilities_db";
        String user = "root";
        String password = "root";
        int startSize = 5;
        int maxSize = 10;
        int validConnectionTimeout = 0;

        try {
            ConnectionPool.getInstance().init(driverClass, jdbcUrl, user, password,
                    startSize, maxSize, validConnectionTimeout);
        } catch (ConnectionPoolException e) {
            LogManager.getLogger().fatal(e);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (ServiceCreator creator = new ServiceCreator()) {
            UserService userService = creator.getUserService();
            List<User> users = userService.findAll();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/jsp/user/list.jsp").forward(req, resp);
        } catch (ServiceCreatorException | ServiceException ex) {
            //TODO: обработать ошибки
            throw new ServletException(ex);
        }
    }
}
