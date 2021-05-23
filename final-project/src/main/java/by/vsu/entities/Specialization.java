package by.vsu.entities;

/**
 * Перечисление возможных специальностей рабочих.
 *
 * @See #Worker
 * @author Kovzov Vladilsav
 */
public enum Specialization {
    ELECTRICIAN("Электрик"), PLUMBER("Водопроводчик"), MECHANIC("Механик");

    private String name;

    Specialization(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
