package candy.ingredient;

import java.util.Objects;

public class Water extends Ingredient {

    public Water(String value) {
        super("water");
        setValue(value);
    }

    @Override
    public String toString() {
        return value + " ml";
    }
}
