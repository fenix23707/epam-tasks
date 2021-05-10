package by.vsu.dao;

/**
 * Бросается когда возникают проблемы при работе с БД.
 *
 * @author Kovzov Vladislav
 */
public class DaoException extends Exception{
    public DaoException(Throwable cause) {
        super(cause);
    }
}
