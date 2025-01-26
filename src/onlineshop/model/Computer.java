package onlineshop.model;

import java.util.Objects;

public class Computer extends Product {
    private ProcessorType processor;
    private RamType ram;

    public Computer(int id, String name, double price, int amountOfAvailable, ProcessorType processor, RamType ram) {
        super(id, name, price, amountOfAvailable, "Computer");
        this.processor = processor;
        this.ram = ram;
    }

    @Override
    public int hashCode() {
        return Objects.hash(processor, ram);
    }

    public void setProcessor(ProcessorType processor) {
        this.processor = processor;
    }

    public void setRam(RamType ram) {
        this.ram = ram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Computer computer)) return false;
        return ram == computer.ram && Objects.equals(processor, computer.processor);
    }

    public ProcessorType getProcessor() {
        return processor;
    }

    public RamType getRam() {
        return ram;
    }

    @Override
    public String toString() {
        return String.format("Komputer: ID: %d, nazwa: %s, cena: %.2f zł, procesor: %s, RAM: %s",
                getId(), getName(), getPrice(), processor.getName(), ram.getSize());
    }

}
