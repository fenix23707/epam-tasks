package by.vsu.entities;

/**
 * Статус заявки. При подачи жильцом заявки она находится в статусе created.
 * Затем, когда ее обработал диспетчер и назначил рабочего, она находится в
 * статусе processed. После выполнения работой рабочем заявка переходит в
 * статус completed. После того как заявка перешла в статус completed.
 * Жилец может подтвердить выполнение работы.
 *
 * @author Kovzov Vladislav
 */
public enum RequestStatus {
    CREATED("создан"),
    PROCESSED("обработан"),
    COMPLETED("выполнен"),
    CONFIRMED("подтвержден");

    private String name;

    RequestStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
