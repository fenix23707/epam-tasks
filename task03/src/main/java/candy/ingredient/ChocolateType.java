package candy.ingredient;

import java.util.Objects;

public class ChocolateType extends Ingredient {
    private String value;

    public ChocolateType(String value) {
        super("chocolateType");
        setValue(value);
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChocolateType that = (ChocolateType) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
