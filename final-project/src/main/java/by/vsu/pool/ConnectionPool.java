package by.vsu.pool;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Класс который хранит и предоставляет соединения с БД. Используется для
 * оптимизации работы с БД. Т.к. каждый раз когда надо сделать что-то просто
 * с БД создание соединения будет забирать большую часть ресурсов.
 *
 * @author Kovzov Vladislav
 */
public final class ConnectionPool {
    private static ConnectionPool instance = new ConnectionPool();

    private String jdbcUrl;

    private String user;

    private  String password;

    private int maxSize = 1;

    private Queue<Connection> freeConnections = new ConcurrentLinkedDeque<>();

    //TODO: решить со способом хранения используемых соединений, или способ их сортировать
    private Set<Connection> usedConnections = new ConcurrentSkipListSet<>(
            (c1,c2)->Integer.compare(c1.hashCode(),c2.hashCode())
    );

    private int validConnectionTimeout;

    private ConnectionPool() {}

    /**
     * Делается для того чтобы во всем приложение был всего 1 объект этого класса
     *
     * @return объект этого класса
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Инициализирует пул соединений
     * @param driverClass указание DriverManager с каким драйвером пытаться
     *                   устанавливать соединение
     * @param jdbcUrl – a database url of the form jdbc:subprotocol:subname
     * @param user для подключения к БД
     * @param password  для подключения к БД
     * @param startSize начальное количество соединений
     * @param maxSize максимальное количество соединений
     * @param validConnectionTimeout время проверки соединения на валидность
     * @throws ConnectionPoolException
     */
    public void init(String driverClass,String jdbcUrl, String user,
                     String password, int startSize, int maxSize,
                     int validConnectionTimeout) throws ConnectionPoolException{
        try {
            Driver driver = (Driver) Class.forName(driverClass).
                    getConstructor().newInstance();
            DriverManager.registerDriver(driver);

            destroy();
            this.jdbcUrl = jdbcUrl;
            this.user = user;
            this.password = password;
            this.maxSize = maxSize;
            this.validConnectionTimeout = validConnectionTimeout;
            for(int i = 0; i < startSize; i++) {
                freeConnections.add(newConnection());
            }
        } catch (InstantiationException  | InvocationTargetException
                | NoSuchMethodException  | IllegalAccessException
                | ClassNotFoundException | SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    /**
     * Получает из списка свободных соединений объект Connection и
     * проверяет его на валидность, если объект не валиден, то
     * получает другой объект. Если объекты закончились и  используемых
     * объектов меньше максимального размера пула, то создается
     * новый объект, в противном случае бросается ConnectionPoolException.
     *
     * @return соединение позволяющее осуществлять запросы в БД
     * @throws ConnectionPoolException если достигнуто максимальное количество
     * соединений или ошибка БД
     * @see #init(String, String, String, String, int, int, int)
     */
    public ConnectionWrapper getConnection() throws ConnectionPoolException {
        Connection connection = null;
        while (connection == null) {
            try {
                connection = freeConnections.poll();
                if (connection != null) {
                    if (!connection.isValid(validConnectionTimeout)) {
                        try {
                            connection.close();
                        } catch (SQLException e) {}
                    }
                } else if (usedConnections.size() < maxSize) {
                    connection = newConnection();
                } else {
                    throw new ConnectionPoolException("database connection" +
                            " limit reached");
                }
            } catch (SQLException ex) {
                throw new ConnectionPoolException(ex);
            }
        }
        usedConnections.add(connection);
        return new ConnectionWrapper(connection);
    }

    /**
     * Переводит connection из используемого в свободный
     *
     * @param connection объект, который нужно перевести из списка занятых в список свободных
     */
    void freeConnection(Connection connection) {
        try {
            connection.clearWarnings();
            if (usedConnections.remove(connection)) {
                freeConnections.add(connection);
            }
        } catch (SQLException ex) {
            try {
                connection.close();
            } catch (SQLException e) {}
        }
    }

    /**
     * Очищает пул соединений, закрывая все соединения.
     */
    public void destroy() {
        synchronized (freeConnections) {
            synchronized (usedConnections) {
                usedConnections.addAll(freeConnections);
                freeConnections.clear();
                for (Connection connection: usedConnections) {
                    try {
                        connection.close();
                    } catch (SQLException e) {}
                }
                usedConnections.clear();
            }
        }
    }

    /**
     * Создает новое соединение
     * @return соединение позволяющее работать с БД
     * @throws ConnectionPoolException при ошибки работы с БД
     */
    private Connection newConnection() throws ConnectionPoolException {
        try {
            return DriverManager.getConnection(jdbcUrl,user,password);
        } catch (SQLException ex) {
            throw new ConnectionPoolException(ex);
        }
    }
}
