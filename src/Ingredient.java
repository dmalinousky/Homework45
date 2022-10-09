import java.io.Serializable;

public enum Ingredient implements Serializable {
    BUN(1),
    SAUSAGE(2),
    LETTUCE(0.5),
    PICKLE(0.3),
    ONION(0.2),
    JALAPENO(0.5),
    CHILI(0.5),
    MUSTARD(0.5),
    KETCHUP(0.5),
    MAYONNAISE(0.5);

    private double unitValue;
    Ingredient(double value) {
        this.unitValue = value;
    }

    public double getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(double unitValue) {
        this.unitValue = unitValue;
    }
}
