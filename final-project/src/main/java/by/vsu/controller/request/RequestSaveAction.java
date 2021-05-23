package by.vsu.controller.request;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Request;
import by.vsu.entities.Tenant;
import by.vsu.entities.User;
import by.vsu.service.RequestService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Получает данные от пользователя:
 *  - description
 *  - desired_day
 * Если данные корректны, то создает объект и сохраняет его. В противном
 * случае уведомляет пользователя об ошибке.
 * В результате направляет на  /request/list.html.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /request/save.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class RequestSaveAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String description = req.getParameter("description");
        String desiredDayStr = req.getParameter("desired_day");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String msg = null;
        if (description == null || description.isBlank()
                || desiredDayStr == null || desiredDayStr.isBlank()) {
            msg = "Данные введены неверно.";
        } else {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("session_user");
            try {
                Date date = dateFormat.parse(desiredDayStr);

                Request request = new Request();
                request.setTenant(new Tenant());
                request.getTenant().setId(user.getId());
                request.setStartDay(date);
                request.setDescription(description);
                RequestService requestService = getServiceFactory().getRequestService();
                requestService.save(request);
            } catch (NullPointerException | IllegalArgumentException | ParseException e) {
                return new Forward(404);
            } catch (ServiceException | ServiceFactoryException e) {
                throw new ServletException(e);
            }
        }
        return new Forward("/request/list.html", msg);
    }
}
