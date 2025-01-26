package onlineshop.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private double price;
    private int itemsAvailable;
    private String type;
    private List<ProductConfiguration> configurations;

    public Product(int id, String name, double price, int itemsAvailable, String type) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.itemsAvailable = itemsAvailable;
        this.type = type;
        this.configurations = new ArrayList<>();
    }

    public void addConfiguration (ProductConfiguration config) {
        if (configurations.contains(config)) {
            System.out.println("Konfiguracja juz istnieje: " + config);
        } else {
            configurations.add(config);
        }
    }

    public List<ProductConfiguration> getConfigurations() {
        return configurations;
    }

    public void removeConfiguration (String type) {
        configurations.removeIf(config -> config.getType().equalsIgnoreCase(type));
    }

    public void displayConfigurations() {
        if (configurations.isEmpty()) {
            System.err.println("Brak konfiguracji dla produktu: " + name);
        } else {
            configurations.forEach(config ->
                    System.out.println(config.getType() + ": " + config.getValue() + "(+ " + config.getPrice() + " zł")
            );
        }
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getItemsAvailable() {
        return itemsAvailable;
    }

    public void setItemsAvailable(int itemsAvailable) {
        this.itemsAvailable = itemsAvailable;
    }

    public double getTotalPrice() {
        return price + configurations.stream()
                .mapToDouble(ProductConfiguration::getPrice)
                .sum();
    }
}
