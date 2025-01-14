package onlineshop.model;

public class Product {
    private int id;
    private String name;
    private double price;
    private int itemsAvailable;

    public Product(int id, String name, double price, int itemsAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemsAvailable = itemsAvailable;
    }

    public void getInfo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemsAvailable() {
        return itemsAvailable;
    }

    public void setItemsAvailable(int itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }

}
