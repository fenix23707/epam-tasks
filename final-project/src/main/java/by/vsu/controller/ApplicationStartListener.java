package by.vsu.controller;

import by.vsu.pool.ConnectionPool;
import by.vsu.pool.ConnectionPoolException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Сервлет, который инициализирует приложение при старте.
 * И разрушает нужные объекты при завершение.
 *
 * @author Kovzov Vladislav
 */
public class ApplicationStartListener implements ServletContextListener {
    private static final Logger logger= LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String driverClass = context.getInitParameter("jdbc-driver");
        String jdbcUrl = context.getInitParameter("jdbc-url");
        String user = context.getInitParameter("jdbc-username");
        String password = context.getInitParameter("jdbc-password");
        int startSize = Integer.parseInt(context.getInitParameter("start-size"));
        int maxSize = Integer.parseInt(context.getInitParameter("max-size"));
        int validConnectionTimeout = Integer.parseInt(
                context.getInitParameter("valid-connection-timeout"));

        try {
            ConnectionPool.getInstance().init(driverClass,jdbcUrl,user,password,
                    startSize,maxSize,validConnectionTimeout);
            logger.info("connection pool was initialized,\n" +
                    "jdbc-drive {}\n" +
                    "jdbc-url {}\n" +
                    "jdbc-user {}\n" +
                    "jdbc-password {}\n" +
                    "start-size {}\n" +
                    "max-size {}\n" +
                    "validConnectionTimeout {}",driverClass,jdbcUrl,user,password,
                    startSize,maxSize,validConnectionTimeout);
        } catch (ConnectionPoolException e) {
            logger.fatal(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("connection pool was destroyed");
        ConnectionPool.getInstance().destroy();
    }
}
