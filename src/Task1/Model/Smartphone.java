package Task1.Model;

public class Smartphone extends Product{
    private String color;
    private int batteryCapacity;
    private String accesory;

    public Smartphone(int id, String name, double price, int amountOfAvailable, String color, int batteryCapacity, String accesory) {
        super(id, name, price, amountOfAvailable);
        this.color = color;
        this.batteryCapacity = batteryCapacity;
        this.accesory = accesory;
    }
}
