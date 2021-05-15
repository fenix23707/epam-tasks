package by.vsu.entities;

/**
 * У каждого пользователя есть своя роль в этой системе. В зависимости от
 * этой роли у него свои возможности.
 */
public enum Role {
    ADMIN("Админ"),
    DISPATCHER("Диспетчер"),
    WORKER("Рабочий"),
    TENANT("Квартиросъемщик");

    private String name;

    private Role(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
