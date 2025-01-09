package Task1.Model;

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

        getComputer();
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

    private void getComputer() {
        System.out.println("Komputer " + getName() + " z procesorem: " + processor + " i RAMem: " + amountOfRam + " GB");
    }
}
