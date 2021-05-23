package by.vsu.service;

import by.vsu.service.exception.TransactionException;

/**
 * Класс предоставляющий упращенный способ работы с транзакциями
 *
 * @author Kovzov Vladislav
 */
public interface Transaction {
    /**
     * Начинает транзакцию. До тех пор пока не будет вызван commit
     * никаких обращений к БД не будет.
     * @throws TransactionException
     */
    void start() throws TransactionException;

    /**
     * Выполняет собранные обращения к БД и завершает транзакцию.
     *
     * @throws TransactionException
     */
    void commit() throws TransactionException;

    /**
     * Отменяет все изменения и завершает транзакцию.
     *
     * @throws TransactionException
     */
    void rollback() throws TransactionException;
}
