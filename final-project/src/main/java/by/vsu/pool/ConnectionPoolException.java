package by.vsu.pool;

/**
 * Бросается при возникновении проблем в ConnectionPool
 *
 * @author Kovzov Vladislav
 */
public class ConnectionPoolException extends Exception{
    public ConnectionPoolException(String message) {
        super(message);
    }

    public ConnectionPoolException(Throwable cause) {
        super(cause);
    }
}
