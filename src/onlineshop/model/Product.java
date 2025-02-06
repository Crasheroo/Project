package onlineshop.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private Type type;
    private List<ProductConfiguration> configurations;

    public Product(int id, String type, String name, double price, int stock, List<ProductConfiguration> configurations) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.configurations = configurations;
    }

    public List<ProductConfiguration> getConfigurations() {
        return configurations;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean isInStock (int quantity) {
        return stock >= quantity;
    }

    public synchronized void decrementStock(int quantity) {
        if (quantity > stock) {
            throw new IllegalArgumentException("Niewystarczajaca ilosc w magazynie!");
        }
        stock -= quantity;
    }

    public synchronized void incrementStock(int quantity) {
        stock += quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(String.format("Produkt: %s\n", name))
                .append(String.format("ID: %d\n", id))
                .append(String.format("Cena: %.2f zł\n", price))
                .append(String.format("Stan magazynowy: %d\n", stock))
                .append(String.format("Typ: %s\n", type != null ? type.name() : "Brak"));

        return sb.toString();
    }
}
