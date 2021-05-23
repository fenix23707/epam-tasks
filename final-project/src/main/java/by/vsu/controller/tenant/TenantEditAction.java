package by.vsu.controller.tenant;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Tenant;
import by.vsu.service.TenantService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Направляет на /tenant/edit.jsp и если был передан id. То находит
 * объект Tenant и сохраняет его в атрибуты.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /tenant/edit.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class TenantEditAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                TenantService tenantService = getServiceFactory().getTenantService();
                Tenant tenant = tenantService.findById(Long.parseLong(id));
                if (tenant != null) {
                    req.setAttribute("tenant", tenant);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (IllegalArgumentException e) {
                /*т.к.  в этот блок программа зайдет только в том случае,
                * если пользователь полазил в настройках сттраницы и специально
                * поменял id, то пояснять почему сервер вернул 404 не нужно.*/
                return new Forward(404);
            }
        }
       return null;
    }
}
