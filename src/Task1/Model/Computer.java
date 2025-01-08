package Task1.Model;

public class Computer extends Product {
    private String processor;
    private int amountOfRam;

    public Computer(int id, String name, double price, int amountOfAvailable, String processor, int amountOfRam) {
        super(id, name, price, amountOfAvailable);
        this.processor = processor;
        this.amountOfRam = amountOfRam;
    }
}
