import java.io.Serializable;
import java.util.ArrayList;

public class Hotdog implements Serializable {
    private ArrayList<Ingredient> recipe;
    private double netPrice;

    public Hotdog() {}

    public Hotdog(ArrayList<Ingredient> recipe) {
        this.recipe = recipe;
    }
    public Hotdog(ArrayList<Ingredient> recipe, double netPrice) {
        this.recipe = recipe;
        this.netPrice = netPrice;
    }

    public ArrayList<Ingredient> getRecipe() {
        return recipe;
    }

    public void setRecipe(ArrayList<Ingredient> recipe) {
        this.recipe = recipe;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double hotdogPriceCalculator() {
        double price = this.getRecipe().stream().mapToDouble(Ingredient::getUnitValue).sum();
        return price;
    }

    @Override
    public String toString() {
        return "Hotdog{" +
                "recipe=" + recipe +
                ", net price=" + netPrice +
                '}';
    }
}
