package candy;

public class Energy {
    private int value;

    public Energy(String value) {
        setValue(value);
    }

    public void setValue(String value) {
        this.value = Integer.parseInt(value);
        if (this.value < 0) {
            throw new IllegalArgumentException("energy can't be negative");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + " кКал";
    }
}
