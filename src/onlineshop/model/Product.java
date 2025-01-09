package onlineshop.model;

import java.util.Scanner;

public class Product {
    private int id;
    private String name;
    private double price;
    private int amountOfAvailable;

    public Product(int id, String name, double price, int amountOfAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amountOfAvailable = amountOfAvailable;
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

    public int getAmountOfAvailable() {
        return amountOfAvailable;
    }

    public void setAmountOfAvailable(int amountOfAvailable) {
        this.amountOfAvailable = amountOfAvailable;
    }

}
