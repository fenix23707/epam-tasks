package by.vsu.controller;

import by.vsu.factories.ServiceFactory;
import by.vsu.factories.ServiceFactoryImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * Принимает все запросы от пользователя. Используя адресс запроса
 * получает класс Action и вызывает в нем метод execute. Получает результат
 * этого метода и по получанному объекту Forward решает что делать дальше.
 * А именно:
 *  -отправить код ошики;
 *  -перенаправить на дргую страницу;
 *  -отобразить jsp страницу;
 *  -показать пользователю сообщение;
 * Так же в этом классе решается какую именно реализацию ServiceFactory
 * использовать. И для каждого action создается объект реализующий
 * интерфейс ServiceFactory.
 *
 * @see Action
 * @see ActionFactory
 * @see Forward
 * @author Kovzov Vladislav
 */
public class DispatcherServlet extends HttpServlet {
    String url = null;
    String context;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private String processUrl(String url, String context) {
        int postfixIndex = url.lastIndexOf(".html");
        if (postfixIndex != -1) {
            url = url.substring(context.length(), postfixIndex);
        } else {
            url = url.substring(context.length());
        }
        return url;
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.context = req.getContextPath();
        this.url = processUrl(req.getRequestURI(), context);

        Action action = ActionFactory.getAction(url);

        Forward forward = null;
        if (action != null) {
            try (ServiceFactory factory = getServiceFactory()) {
                action.setServiceFactory(factory);
                forward = action.execute(req, resp);
            } catch (Exception e) {
                forward = new Forward(500);
                LogManager.getLogger().error(e);
                /*throw new ServletException(e);*/
            }
        }

        processForward(forward,req,resp);
    }

    private void processForward(Forward forward, HttpServletRequest req,
                                HttpServletResponse resp) throws ServletException, IOException{


        String msg = null;
        if (forward != null && forward.getErrorCode() != null) {//была ошибка
            resp.sendError(forward.getErrorCode());
            return;
        }

        if (forward == null || forward.getUrl() == null) {
            req.getRequestDispatcher("/WEB-INF/jsp" + url + ".jsp").forward(req, resp);
            return;
        }

        if (forward.getMsg() != null) {                         //было задано сообщение
            msg = "?message=" + URLEncoder.encode(forward.getMsg(), "UTF-8");
        }
        if (forward.isRedirect()) {
            String address = context + forward.getUrl();
            if (msg != null) {
                address += msg;
            }
            resp.sendRedirect(address);
        } else {
            req.getRequestDispatcher(forward.getUrl()).forward(req, resp);
        }
    }

    public ServiceFactory getServiceFactory() {
        return new ServiceFactoryImpl();
    }
}
