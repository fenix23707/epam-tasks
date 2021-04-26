package candy.ingredient;

import java.util.Objects;

public class Sugar extends Ingredient{
    public Sugar(String value) {
        super("sugar");
        setValue(value);
    }

    @Override
    public String toString() {
        return value + " mg";
    }
}
