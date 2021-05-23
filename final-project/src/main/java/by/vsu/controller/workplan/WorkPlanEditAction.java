package by.vsu.controller.workplan;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.Request;
import by.vsu.entities.WorkPlan;
import by.vsu.service.RequestService;
import by.vsu.service.WorkPlanService;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Подготавляивает данные нужные для workplan/edit.jsp. А именно:
 *  - список всех рабочих
 *  - request
 *  - сегодняшнюю дату
 *  - и если это не создания нового, то еще workPlan
 * Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /workplan/edit.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class WorkPlanEditAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String workPlanIdStr = req.getParameter("work_plan_id");
        try {
            if (workPlanIdStr != null) {
                WorkPlanService workPlanService = getServiceFactory().getWorkPlanService();
                WorkPlan workPlan = workPlanService.findById(Long.valueOf(workPlanIdStr));
                if (workPlan != null) {
                    req.setAttribute("work_plan", workPlan);
                } else {
                    throw new IllegalArgumentException();
                }
            }

            Long requestId = Long.valueOf(req.getParameter("request_id"));
            RequestService requestService = getServiceFactory().getRequestService();
            Request request = requestService.findById(requestId);
            req.setAttribute("request", request);
            req.setAttribute("workers", getServiceFactory().getWorkerService().findAllByActive(true));
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        } catch (IllegalArgumentException | NullPointerException e) {
            return new Forward(404);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        req.setAttribute("today", dateFormat.format(new Date()));
        return null;
    }
}
