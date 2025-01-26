package onlineshop.model;

public class ProductConfiguration {
    private String type;
    private String value;
    private double price;

    public ProductConfiguration(String type, String value, double price) {
        this.type = type;
        this.value = value;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Typ: " + type + ", Wartość: " + value + ", Cena: " + price + " zł";
    }
}
