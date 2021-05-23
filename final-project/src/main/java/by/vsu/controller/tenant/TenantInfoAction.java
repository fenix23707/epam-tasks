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
 * Находит объект Tenant по переданному id, сохраняет его в атрибуты
 * и направляет на /tenant/info.jsp
 * В противном случае сообщает пользователю об ошибке.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /tenant/info.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class TenantInfoAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TenantService tenantService  = getServiceFactory().getTenantService();

            Tenant tenant = tenantService.findById(Long.valueOf(
                    req.getParameter("tenant_id")));
            req.setAttribute("tenant",tenant);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
        return null;
    }
}
