package onlineshop.model;

public enum SmartphoneAccesories {
    charger(1, 100),
    lead(2, 20),
    phoneCase(3, 50);

    private int option;
    private double price;

    SmartphoneAccesories(int option, double price) {
        this.option = option;
        this.price = price;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
