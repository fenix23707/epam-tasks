package by.vsu.web;

import by.vsu.pool.ConnectionPool;
import by.vsu.pool.ConnectionPoolException;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ApplicationStartListener implements ServletContextListener {
    private static final Logger logger= LogManager.getLogger();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String driverClass = "com.mysql.cj.jdbc.Driver";
        String jdbcUrl = "jdbc:mysql://localhost:3306/housing_and_utilities_db";
        String user = "root";
        String password = "root";
        int startSize = 5;
        int maxSize = 10;
        int validConnectionTimeout = 0;

        try {
            ConnectionPool.getInstance().init(driverClass,jdbcUrl,user,password,
                    startSize,maxSize,validConnectionTimeout);
        } catch (ConnectionPoolException e) {
            logger.fatal(e);
        }
    }
}
