package bg.smg.pharmacy.model;

public class Ingredient{
    private Integer ingredient_id;
    private String ingredientName;
    private Integer ingredientWeight;

    public Ingredient(String ingredientName, int ingredientWeight) {
        this.ingredientName = ingredientName;
        this.ingredientWeight = ingredientWeight;
    }

    public Integer getIngredientId() {
        return ingredient_id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public Integer getIngredientWeight() {
        return ingredientWeight;
    }

}

