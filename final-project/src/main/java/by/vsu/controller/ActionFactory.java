package by.vsu.controller;

import by.vsu.controller.dispather.DispatcherEditAction;
import by.vsu.controller.dispather.DispatcherSaveAction;
import by.vsu.controller.request.*;
import by.vsu.controller.tenant.TenantEditAction;
import by.vsu.controller.tenant.TenantInfoAction;
import by.vsu.controller.tenant.TenantSaveAction;
import by.vsu.controller.user.UserDeleteAction;
import by.vsu.controller.user.UserEditAction;
import by.vsu.controller.user.UserListAction;
import by.vsu.controller.worker.WorkerEditAction;
import by.vsu.controller.worker.WorkerListByWorkPlanIdAction;
import by.vsu.controller.worker.WorkerSaveAction;
import by.vsu.controller.workplan.WorkPlanCreateAction;
import by.vsu.controller.workplan.WorkPlanEditAction;
import by.vsu.controller.workplan.WorkPlanListAction;
import by.vsu.controller.workplan.WorkPlanSaveAction;
import jakarta.servlet.ServletException;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Хранит для кажого url свой класс Action и в зависимости от переданного url
 * возвращает нужный класс.
 *
 * @see Action
 * @author Kovzov Vladislav
 */
public class ActionFactory {
    /*Используется Class<?> потому что если передать
    сюда объект этого класса, то т.к. поле статическое то для всех запрососв будет
     использоваться этот объект, а т.к. в объекте есть конекшен, то для всех
     запрососв будет использоваться один и тот же connection*/
    private static Map<String, Class<? extends Action>> actions = new HashMap<>();
    static {
        actions.put("/",MainAction.class);
        actions.put("/index", MainAction.class);
        actions.put("/login", LoginAction.class);
        actions.put("/logout", LogoutAction.class);

        actions.put("/user/list", UserListAction.class);
        actions.put("/user/edit", UserEditAction.class);
        actions.put("/user/delete", UserDeleteAction.class);

        actions.put("/tenant/edit", TenantEditAction.class);
        actions.put("/tenant/save", TenantSaveAction.class);
        actions.put("/tenant/info", TenantInfoAction.class);

        actions.put("/request/list", RequestListAction.class);
        actions.put("/request/add", RequestAddAction.class);
        actions.put("/request/save", RequestSaveAction.class);
        actions.put("/request/delete", RequestDeleteAction.class);
        actions.put("/request/info", RequestInfoAction.class);
        actions.put("/request/change_status", RequestChangeStatusAction.class);

        actions.put("/dispatcher/edit", DispatcherEditAction.class);
        actions.put("/dispatcher/save", DispatcherSaveAction.class);

        actions.put("/worker/edit", WorkerEditAction.class);
        actions.put("/worker/save", WorkerSaveAction.class);
        actions.put("/worker/list_by_work_plan_id", WorkerListByWorkPlanIdAction.class);

        actions.put("/workplan/list", WorkPlanListAction.class);
        actions.put("/workplan/create", WorkPlanCreateAction.class);
        actions.put("/workplan/edit", WorkPlanEditAction.class);
        actions.put("/workplan/save", WorkPlanSaveAction.class);

    }

    /**
     * Создает объект класса Action для определнного url
     * @param url для которого нужен объект класса Action
     * @return объект класса Action или null, если для переданного url
     * не нашлось соответсвующих объектов
     * @throws ServletException
     */
    public static Action getAction(String url) throws ServletException {
        LogManager.getLogger().debug("action factory: " + url);
        Class<? extends Action> action = actions.get(url);
        if (action != null) {
            try {
                LogManager.getLogger().debug(url);
                return (Action) action.getConstructor().newInstance();
            } catch (NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException | InstantiationException e) {
                throw new ServletException(e);
            }
        } else {
            return null;
        }
    }
}
