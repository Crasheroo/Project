package onlineshop.model;

import java.util.Objects;
import java.util.Scanner;

public class Computer extends Product {
    private String processor;
    private int amountOfRam;

    public Computer(int id, String name, double price, int amountOfAvailable, String processor, int amountOfRam) {
        super(id, name, price, amountOfAvailable);
        this.processor = processor;
        this.amountOfRam = amountOfRam;
    }

    public void configureSpecification() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Wybierz procesor: ");
        processor = scanner.nextLine();

        System.out.print("Wybierz ilość RAM (GB): ");
        amountOfRam = scanner.nextInt();
        scanner.nextLine();

        getInfo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(processor, amountOfRam);
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getAmountOfRam() {
        return amountOfRam;
    }

    public void setAmountOfRam(int amountOfRam) {
        this.amountOfRam = amountOfRam;
    }

    @Override
    public void getInfo() {
        System.out.println("Komputer " + getName() + " z procesorem: " + processor + " i RAMem: " + amountOfRam + " GB, cena: " + getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer computer)) return false;
        return amountOfRam == computer.amountOfRam && Objects.equals(processor, computer.processor);
    }

    @Override
    public String toString() {
        return "Computer{" +
                "processor='" + processor + '\'' +
                ", amountOfRam=" + amountOfRam +
                '}';
    }
}
