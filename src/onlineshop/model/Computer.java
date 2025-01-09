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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer computer)) return false;
        return amountOfRam == computer.amountOfRam && Objects.equals(processor, computer.processor);
    }

    @Override
    public String toString() {
        return "Komputer: ID: " + getId() + ", nazwa: " + getName() + ", cena: " + getPrice() + ", procesor: " + processor + ", ram: " + amountOfRam + " GB";
    }
}
