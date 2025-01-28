package onlineshop.model;

public class ProductConfiguration {
    private Type type;
    private String value;
    private double price;

    public ProductConfiguration(Type type, String value, double price) {
        this.type = type;
        this.value = value;
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public double getPrice() {
        return price;
    }


    @Override
    public String toString() {
        return "Typ: " + type + ", Wartość: " + value + ", Cena: " + price + " zł";
    }
}
