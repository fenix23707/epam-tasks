package by.vsu.service;

/**
 * Базовый класс для всех сервисов в этом приложении.
 *
 * @author kovzov Vladislav
 */
public class BaseService {
    private Transaction transaction;

    protected Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
