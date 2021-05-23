package by.vsu.controller.workplan;

import by.vsu.controller.Action;
import by.vsu.controller.Forward;
import by.vsu.factories.ServiceFactoryException;
import by.vsu.entities.*;
import by.vsu.service.exception.ServiceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Получает информацию от пользователя проверяет ее и если вся
 * информация корректна создает объект класса WorkPlan и сохраняет
 * его. Объект этого класса создается когда пользователь переходит
 * на старницу с адресом: /workplan/save.html
 *
 * @see Action
 * @see by.vsu.controller.ActionFactory
 * @author Kovzov Vladislav
 */
public class WorkPlanSaveAction extends Action {
    @Override
    public Forward execute(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Request request;
        WorkPlan workPlan;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Long requestId = Long.valueOf(req.getParameter("request_id"));
            request = getServiceFactory().getRequestService().findById(requestId);
            request.setStartDay(dateFormat.parse(req.getParameter("start_day")));
            String[] workersIdStr = req.getParameterValues("workers_id");
            if (workersIdStr == null || workersIdStr.length == 0) {
                String msg = "Изменения не произошли, т.к. нужно назначить рабочих.";
                return new Forward("/workplan/list.html", msg);
            }
            workPlan = assembleWorkPlan(request, req, dateFormat, workersIdStr);
        } catch (NumberFormatException | ServiceFactoryException | ParseException e) {
            return new Forward(404);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        try {
            workPlan.getRequest().setStatus(RequestStatus.PROCESSED);
            getServiceFactory().getWorkPlanService().save(workPlan);
        } catch (ServiceFactoryException | ServiceException e) {
            throw new ServletException(e);
        }

        return new Forward("/workplan/list.html");

    }

    private WorkPlan assembleWorkPlan(Request request, HttpServletRequest req,
                                      DateFormat dateFormat, String[] workersIdStr)
            throws ParseException {
        WorkPlan workPlan = new WorkPlan();
        String workPlanId = req.getParameter("work_plan_id");
        if (workPlanId != null) {
            workPlan.setId(Long.valueOf(workPlanId));
        }
        workPlan.setWorkScope(Integer.valueOf(req.getParameter("work_scope")));
        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("session_user");
        workPlan.setDispatcher(new Dispatcher());
        workPlan.getDispatcher().setId(user.getId());
        workPlan.setRequest(request);
        Date endDay = dateFormat.parse(req.getParameter("end_day"));
        workPlan.setEndDay(endDay);
        List<Worker> workers = new ArrayList<>();
        workPlan.setWorkers(workers);
        List<Long> workersId = Arrays.stream(workersIdStr).
                map(Long::valueOf).collect(Collectors.toList());
        for (Long workerId : workersId) {
            Worker worker = new Worker();
            worker.setId(workerId);
            workers.add(worker);
        }
        return workPlan;
    }
}
