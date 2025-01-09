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
        super(id, name, price, amountOfAvailable);
        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.accesories = new ArrayList<>();
    }

    public void chooseSpecification() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Wybierz kolor: ");
        color = scanner.nextLine();

        System.out.print("Wybierz pojemność baterii (mAh): ");
        batteryCapacity = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Dodawanie akcesoriów. ('koniec', żeby zakończyć)\n");
        while (true) {
            System.out.print("Dodaj akcesorium: ");
            String accesory = scanner.nextLine();
            if (accesory.equalsIgnoreCase("koniec")) {
                break;
            }
            accesories.add(accesory);
        }
        getInfo();
    }

    @Override
    public void getInfo() {
        System.out.println("Smartfon: " + getName() + " z kolorem " + color + ", baterią: " + batteryCapacity + " mAh i akcesoriami: " + accesories + ", cena: " + getPrice());
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public List<String> getAccesories() {
        return accesories;
    }

    public void setAccesories(List<String> accesories) {
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
        return "Smartphone{" +
                "color='" + color + '\'' +
                ", batteryCapacity=" + batteryCapacity +
                ", accesories=" + accesories +
                '}';
    }
}
