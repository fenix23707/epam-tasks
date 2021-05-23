package by.vsu.entities;

import java.util.Date;

/**
 * Хранит необходимую информацию о заявке.
 *
 * @author Kovzov Vladislav
 */
public class Request extends Entity{
    private Specialization specialization;

    private String description;

    Date startDay;

    Tenant tenant;

    RequestStatus status = RequestStatus.CREATED;

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Request{" +
                "specialization=" + specialization +
                ", workScope=" + description +
                ", desiredDay=" + startDay +
                ", tenant=" + tenant +
                ", status=" + status +
                '}';
    }
}
