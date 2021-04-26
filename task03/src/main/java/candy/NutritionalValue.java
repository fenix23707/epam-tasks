package candy;

public class NutritionalValue {
    private float protein;
    private float fats;
    private float carbohydrates;

    public NutritionalValue(String protein, String fats, String carbohydrates) {
        setProtein(protein);
        setFats(fats);
        setCarbohydrates(carbohydrates);
    }

    public void setProtein(String protein) {
        this.protein = Float.parseFloat(protein);
        if (this.protein < 0) {
            throw new IllegalArgumentException("protein is negative");
        }
    }

    public void setFats(String fats) {
        this.fats = Float.parseFloat(fats);
        if (this.fats < 0) {
            throw new IllegalArgumentException("fats is negative");
        }
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = Float.parseFloat(carbohydrates);
        if (this.carbohydrates < 0) {
            throw new IllegalArgumentException("carbohydrates is negative");
        }
    }

    public float getProtein() {
        return protein;
    }

    public float getFats() {
        return fats;
    }

    public float getCarbohydrates() {
        return carbohydrates;
    }

    @Override
    public String toString() {
        return protein + " gr "
                + fats + " gr "
                + carbohydrates + " gr";
    }
}
