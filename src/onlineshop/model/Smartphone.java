package onlineshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Smartphone extends Product{
    private String color;
    private int batteryCapacity;
    private List<String> accesories;

    public Smartphone(int id, String name, double price, int amountOfAvailable, String color, int batteryCapacity) {
        super(id, name, price, amountOfAvailable, "Smartphone");
        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.accesories = new ArrayList<>();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<String> getAccesories() {
        return accesories;
    }

    public void addAcceossory(String accessory) {
        accesories.add(accessory);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Smartphone that)) return false;
        return batteryCapacity == that.batteryCapacity && Objects.equals(color, that.color) && Objects.equals(accesories, that.accesories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, batteryCapacity, accesories);
    }

    @Override
    public String toString() {
        return "Smartfon: ID: " + getId() + ", nazwa: " + getName() + " z kolorem " + color + ", baterią: " + batteryCapacity + ", cena: " + getPrice();
    }
}
