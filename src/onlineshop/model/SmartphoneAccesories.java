package onlineshop.model;

public enum SmartphoneAccesories {
    charger(1, 100),
    lead(2, 20),
    phoneCase(3, 50);

    private int id;
    private double price;

    SmartphoneAccesories(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public double getPrice() {
        return price;
    }
}
