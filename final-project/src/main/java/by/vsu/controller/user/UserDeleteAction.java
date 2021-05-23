package by.vsu.controller.user;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.service.UserService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает id, и удаляет пользователя с этим id. Затем направляет на
 * /user/list.html с сообщением о результате.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /user/delete.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class UserDeleteAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String msg = null;
        String id = req.getParameter("id");
        if (id != null && !id.isBlank()) {
            try {
                UserService userService = getServiceFactory().getUserService();
                if (userService.delete(Long.valueOf(id))) {
                    msg = "Пользователь удален";
                } else {
                    msg = "Не удалось удалить пользователя";
                }
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (NumberFormatException e) {
                return new Forward(404);
            }
        }

        return new Forward("/user/list.html", msg);
    }
}
