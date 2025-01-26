package onlineshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Smartphone extends Product{
    private SmartphoneColors color;
    private int batteryCapacity;
    private List<SmartphoneAccesories> accesories;

    public Smartphone(int id, String name, double price, int amountOfAvailable, SmartphoneColors color, int batteryCapacity) {
        super(id, name, price, amountOfAvailable, "Smartphone");
        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.accesories = new ArrayList<>();
    }

    public void addAccessory(SmartphoneAccesories accessory) {
        accesories.add(accessory);
    }

    public List<SmartphoneAccesories> getAccesories() {
        return accesories;
    }

    public void setColor(SmartphoneColors color) {
        this.color = color;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public void setAccesories(List<SmartphoneAccesories> accesories) {
        this.accesories = accesories;
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
        String accessoriesInfo = accesories.isEmpty()
                ? "Brak akcesoriów"
                : accesories.stream()
                .map(SmartphoneAccesories::name)
                .reduce((a, b) -> a + ", " + b)
                .orElse("Brak akcesoriów");

        return String.format("Smartfon: ID: %d, nazwa: %s, kolor: %s, bateria: %dmAh, cena: %.2f zł, akcesoria: [%s]",
                getId(), getName(), color, batteryCapacity, getPrice(), accessoriesInfo);
    }
}
