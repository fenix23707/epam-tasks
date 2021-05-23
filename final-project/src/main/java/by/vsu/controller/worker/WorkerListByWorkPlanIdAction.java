package by.vsu.controller.worker;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Worker;
import by.vsu.service.WorkerService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * Получает work_plan_id для этого id находит список всех рабочих.
 * И в случае успеха передает его на /worker/list_by_work_plan_id.jsp
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /worker/list_by_work_plan_id.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class WorkerListByWorkPlanIdAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workPlanId = req.getParameter("work_plan_id");
        try {
            WorkerService workerService  = getServiceFactory().getWorkerService();
            List<Worker> workers = workerService.findAllByWorkPlanId(Long.valueOf(workPlanId));
            req.setAttribute("workers",workers);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (NumberFormatException e) {
            return new Forward(404);
        }
        return null;
    }
}
