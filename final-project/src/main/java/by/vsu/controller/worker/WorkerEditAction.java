package by.vsu.controller.worker;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Specialization;
import by.vsu.entities.Worker;
import by.vsu.service.WorkerService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Направляет на /worker/edit.jsp. Если был передан id, то находит рабочего
 * с этим id и сохраняет его в атрибуты.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /worker/edit
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class WorkerEditAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            try {
                WorkerService workerService = getServiceFactory().getWorkerService();
                Worker worker = workerService.findById(Long.parseLong(id));
                if (worker != null) {
                    req.setAttribute("worker", worker);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (ServiceFactoryException | ServiceException e) {
                throw new ServletException(e);
            } catch (IllegalArgumentException e) {
                return new Forward(404);
            }
        }
        req.setAttribute("specializations", Specialization.values());
        return null;
    }
}
