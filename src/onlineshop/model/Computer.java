package onlineshop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Computer extends Product {
    private ProcessorType processor;
    private RamType ram;
    private List<ProductConfiguration> configurations;

    public Computer(int id, String name, double price, int amountOfAvailable, ProcessorType processor, RamType ram) {
        super(id, name, price, amountOfAvailable, "Computer");
        this.processor = processor;
        this.ram = ram;
        this.configurations = new ArrayList<>();
        addConfiguration(new ProductConfiguration("Processor", processor.getName(), processor.getPrice()));
        addConfiguration(new ProductConfiguration("RAM", ram.getSize(), ram.getPrice()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(processor, ram);
    }

    public void setProcessor(ProcessorType processor) {
        this.processor = processor;
        addConfiguration(new ProductConfiguration("Processor", processor.getName(), processor.getPrice()));
    }

    public void addConfiguration(ProductConfiguration configuration) {
        configurations.add(configuration);
    }

    @Override
    public List<ProductConfiguration> getConfigurations() {
        return configurations;
    }

    public void setRam(RamType ram) {
        this.ram = ram;
        addConfiguration(new ProductConfiguration("RAM", ram.getSize(), ram.getPrice()));
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
