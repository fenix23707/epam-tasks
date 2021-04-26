package candy.ingredient;

import java.util.Objects;

public class Fructose extends Ingredient{

    public Fructose(String value) {
        super("fructose");
        setValue(value);
    }

    @Override
    public String toString() {
        return value + " mg";
    }
}
