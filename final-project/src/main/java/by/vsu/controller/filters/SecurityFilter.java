package by.vsu.controller.filters;

import by.vsu.entities.Role;
import by.vsu.entities.User;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * При переходе на любую страницу вначале вызывается SecurityFilter
 * и проверяет можно ли пользователю с данной ролью перейти на эту
 * страницу. Если нельзя, перенапрвляет его на /login.html с сообщением
 * об ошибке.
 *
 * @author Kovzov Vladislav
 */
public class SecurityFilter implements Filter {
    private static final Map<String, Set<Role>> permissions = new HashMap<>();
    static {
        Set<Role> all = new HashSet<>(Arrays.asList(Role.values()));

        Set<Role> admin = new HashSet<>();
        admin.add(Role.ADMIN);

        Set<Role> tenant = new HashSet<>();
        tenant.add(Role.TENANT);

        Set<Role> dispatcher = new HashSet<>();
        dispatcher.add(Role.DISPATCHER);

        Set<Role> dispatcherAndWorker = new HashSet<>();
        dispatcherAndWorker.add(Role.DISPATCHER);
        dispatcherAndWorker.add(Role.WORKER);

        Set<Role> workerAndTenant = new HashSet<>();
        workerAndTenant.add(Role.WORKER);
        workerAndTenant.add(Role.TENANT);

        permissions.put("/logout",all);

        permissions.put("/user/list",admin);
        permissions.put("/user/edit",admin);
        permissions.put("/user/save",admin);
        permissions.put("/user/delete",admin);

        permissions.put("/tenant/edit",admin);
        permissions.put("/tenant/save", admin);
        permissions.put("/tenant/info", dispatcherAndWorker);

        permissions.put("/dispatcher/edit", admin);
        permissions.put("/dispatcher/save", admin);

        permissions.put("/request/list", tenant);
        permissions.put("/request/add", tenant);
        permissions.put("/request/save", tenant);
        permissions.put("/request/delete", dispatcher);
        permissions.put("/request/info", dispatcherAndWorker);
        permissions.put("/request/change_status", workerAndTenant);

        permissions.put("/worker/edit", admin);
        permissions.put("/worker/save", admin);
        permissions.put("/worker/list_by_work_plan_id", dispatcherAndWorker);

        permissions.put("/workplan/list", dispatcherAndWorker);
        permissions.put("/workplan/create", dispatcher);
        permissions.put("/workplan/edit", dispatcher);
        permissions.put("/workplan/save", dispatcher);
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResp = (HttpServletResponse) servletResponse;

        String url = httpReq.getRequestURI();
        String context = httpReq.getContextPath();
        int postfixIndex = url.lastIndexOf(".html");
        if (postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        Set<Role> roles = permissions.get(url);
        if (roles != null) {
            HttpSession session = httpReq.getSession(false);
            if (session != null) {
                User user = (User)session.getAttribute("session_user");
                if (user != null && roles.contains(user.getRole())) {
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        String msg = "Доступ запрещен";
        httpResp.sendRedirect(context + "/login.html?message=" + URLEncoder.encode(msg, "UTF-8"));
    }
}
