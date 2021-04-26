package candy;

import candy.ingredient.Ingredient;

import java.util.HashSet;
import java.util.Set;

public class Candy {
    private String id;
    private String name;
    private Energy energy;
    private CandyType candyType;
    private Set<Ingredient> ingredients;
    private NutritionalValue value;
    private String production;

    public Candy() {
        ingredients = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public Energy getEnergy() {
        return energy;
    }

    public CandyType getCandyType() {
        return candyType;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public NutritionalValue getValue() {
        return value;
    }

    public String getProduction() {
        return production;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEnergy(Energy energy) {
        this.energy = energy;
    }

    public void setCandyType(CandyType candyType) {
        this.candyType = candyType;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void setValue(NutritionalValue value) {
        this.value = value;
    }

    public void setProduction(String production) {
        this.production = production;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("id: ").append(id).append(";\n");
        builder.append("name: ").append(name).append(";\n");
        builder.append("energy: ").append(energy).append(";\n");
        builder.append("candyType").append(candyType).append(";\n");
        builder.append("ingredients: ");
        for (Ingredient ingredient : ingredients) {
            builder.append(ingredient.getName()).append(": ")
                    .append(ingredient).append("; ");
        }
        builder.append("\n");
        builder.append("value: ").append(value).append(";\n");
        builder.append("production: ").append(production).append(";\n");

        return builder.toString();
    }
}
