package candy.ingredient;

import java.util.Objects;

public class Vanillin extends Ingredient{

    public Vanillin(String value) {
        super("vanillin");
        setValue(value);
    }

    @Override
    public String toString() {
        return value + " mg";
    }

}
