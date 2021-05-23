package by.vsu.controller;

import by.vsu.factories.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Основной класс для всех запросов от пользователя. Все наследники
 * должны реализовать метод execute.
 *
 * @see DispatcherServlet
 * @see ActionFactory
 * @author Kovzov Vladislav
 */
public abstract class Action {
    private ServiceFactory serviceFactory;

    public final ServiceFactory getServiceFactory() {
        return serviceFactory;
    }

    public final void setServiceFactory(ServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    abstract public Forward execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException , IOException;
}
