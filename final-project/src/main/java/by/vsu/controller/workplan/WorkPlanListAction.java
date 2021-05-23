package by.vsu.controller.workplan;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.*;
import by.vsu.service.WorkPlanService;
import by.vsu.service.WorkerService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

/**
 * Подготавливает данные для отображения их при помощи /worlkplan/list.jsp
 *  - Для диспечера это список созданных им рабочих планов.
 *  - Для рабочего это список рабочих планов в котоорых он участвует.
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /workplan/list.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class WorkPlanListAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            User user = (User) session.getAttribute("session_user");
            List<WorkPlan> workPlans = null;
            if (user.getRole() == Role.DISPATCHER) {
                WorkPlanService workPlanService = getServiceFactory().getWorkPlanService();
                workPlans = workPlanService.findAllByDispatcherId(user.getId());
            } else if (user.getRole() == Role.WORKER) {
                WorkerService workerService = getServiceFactory().getWorkerService();
                Worker worker = workerService.findById(user.getId());
                req.setAttribute("worker", worker);
                workPlans = worker.getWorkPlans();
            }
            req.setAttribute("work_plans", workPlans);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }
        return null;
    }
}
