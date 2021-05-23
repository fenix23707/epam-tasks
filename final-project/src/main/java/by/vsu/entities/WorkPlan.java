package by.vsu.entities;

import java.util.Date;
import java.util.List;

/**
 * @author Kovzov Vladislav
 */
public class WorkPlan extends Entity{
    private Date endDay;

    private Request request = null;

    private Dispatcher dispatcher = null;

    private Integer workScope = null;

    private List<Worker> workers = null;

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public Integer getWorkScope() {
        return workScope;
    }

    public void setWorkScope(Integer workScope) {
        this.workScope = workScope;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public List<Worker> getWorkers() {
        return workers;
    }

    public void setWorkers(List<Worker> workers) {
        this.workers = workers;
    }
}
