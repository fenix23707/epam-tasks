package by.vsu.entities;

import java.io.Serializable;
import java.util.Locale;

/**
 * Entity это абстрактный основной класс для всех объектов лежащих в БД.
 * @author Kovzov Vladislav
 */
abstract public class Entity implements Serializable {
    private Long id;

    /**
     * Возвращает id по которому можно получить доступ к объекту в БД.
     * @return  id объекта в БД
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id уникальный идентификатор этого объекта в БД.
     */
    public void setId(Long id) {
        this.id = id;
    }
}
