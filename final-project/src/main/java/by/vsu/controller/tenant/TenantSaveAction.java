package by.vsu.controller.tenant;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Role;
import by.vsu.entities.Tenant;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает данные от пользователя, проверяет их. И если данные корректны
 * создает объект класса Tenant и сохраняет его. И направляет на /user/list.html
 * В противном случае сообщает пользователю об ошибке.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /tenant/save.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class TenantSaveAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        Long id = null;
        int apartmentNumber;
        try {
            apartmentNumber = Integer.parseInt(req.getParameter("apartment_number"));
            String idStr = req.getParameter("id");
            if (idStr != null) {
                id = Long.valueOf(idStr);
            }
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
        String msg = null;
        if (login != null && !login.isBlank()
                && password != null && !password.isBlank()
                && name != null && !name.isBlank()
                && address != null && !address.isBlank()) {
            Tenant tenant = new Tenant();
            tenant.setId(id);
            tenant.setLogin(login);
            tenant.setPassword(password);
            tenant.setName(name);
            tenant.setRole(Role.TENANT);
            tenant.setAddress(address);
            tenant.setApartmentNumber(apartmentNumber);
            try {
                getServiceFactory().getTenantService().save(tenant);
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            msg = "Данные введены не верно.";
        }
        return new Forward("/user/list.html", msg);
    }
}
