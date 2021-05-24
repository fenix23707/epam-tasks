package by.vsu.controller.worker;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Role;
import by.vsu.entities.Specialization;
import by.vsu.entities.Worker;
import by.vsu.service.exception.LoginAlreadyExistException;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Получает данные:
 * - login
 * - password
 * - name
 * - specialization
 * - и если было редактирование существующего worker, то еще id
 * Проверяет их на корректность, создает объект класса Worker и сохраняет его.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /worker/save.html
 *
 * @author Kovzov Vladislav
 * @see Action
 * @see by.vsu.controller.ActionFactory
 */
public class WorkerSaveAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String name = req.getParameter("name");
        Long id = null;
        String spec = req.getParameter("specialization");
        try {
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
                && name != null && !name.isBlank() && spec != null) {
            Worker worker = new Worker();
            worker.setId(id);
            worker.setLogin(login);
            worker.setPassword(password);
            worker.setName(name);
            worker.setRole(Role.WORKER);
            worker.setSpecialization(Specialization.valueOf(spec));
            try {
                getServiceFactory().getWorkerService().save(worker);
            } catch (LoginAlreadyExistException e) {
                msg = "Такой логин уже существует";
            } catch (NullPointerException | IllegalArgumentException e) {
                return new Forward(404);
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            }
        } else {
            msg = "Данные введены неверно.";
        }
        return new Forward("/user/list.html", msg);
    }
}
