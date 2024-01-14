package bg.smg.pharmacy.model;

public class Ingredient{
    private String ingredientName;
    private int ingredientWeight;

    public Ingredient(String ingredientName, int ingredientWeight) {
        this.ingredientName = ingredientName;
        this.ingredientWeight = ingredientWeight;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public int getIngredientWeight() {
        return ingredientWeight;
    }
}

