package onlineshop.service;

import onlineshop.model.*;

import java.util.Scanner;

public class ProductConfigurationService {
    private final Scanner scanner;

    public ProductConfigurationService(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Rozpoczyna proces konfiguracji
     */
    public void configureProduct(Product product) {
        if (product instanceof Smartphone smartphone) {
            configureSmartphone(smartphone);
        } else if (product instanceof Computer computer) {
            configureComputer(computer);
        } else {
            System.err.println("Nie ma konfiguracji dla tego typu produktu");
        }
    }

    private void configureComputer(Computer computer) {
        while (true) {
            System.out.println("Konfigurowanie komputera:");
            System.out.println("1 - Zmień procesor");
            System.out.println("2 - Zmień RAM");
            System.out.println("3 - Wyświetl konfiguracje");
            System.out.println("0 - Wyjdź");
            System.out.print("Wybierz opcję: ");

            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0 -> {
                    System.out.println("Zakończono konfigurację komputera.");
                    return;
                }
                case 1 -> chooseProcessor(computer);
                case 2 -> chooseRam(computer);
                case 3 -> System.out.println(computer);
                default -> System.err.println("Niepoprawna opcja. Wybierz ponownie.");
            }
        }
    }

    private void configureSmartphone(Smartphone smartphone) {
        while (true) {
            System.out.println("Konfigurowanie smartfona:");
            System.out.println("1 - Zmień kolor");
            System.out.println("2 - Dodaj akcesorium");
            System.out.println("3 - Wyświetl konfiguracje");
            System.out.println("0 - Wyjdź");
            System.out.print("Wybierz opcję: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0 -> {
                    System.out.println("Zakonczono konfiguracje telefonu");
                    return;
                }

                case 1 -> chooseColor(smartphone);
                case 2 -> chooseAccessory(smartphone);
                case 3 -> System.out.println(smartphone);
                default -> System.err.println("Niepoprawna opcja");
            }
        }
    }

    private void chooseColor(Smartphone smartphone) {
        System.out.println("Dostępne kolory:");
        for (SmartphoneColors color : SmartphoneColors.values()) {
            System.out.printf("%d - %s%n", color.getId(), color.getColor());
        }
        System.out.print("Wybierz numer koloru: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        for (SmartphoneColors color : SmartphoneColors.values()) {
            if (color.getId() == choice) {
                smartphone.setColor(color);
                ProductConfiguration config = new ProductConfiguration("Color", color.getColor(), 0.0);
                smartphone.addConfiguration(config);

                System.out.printf("Zmieniono kolor na: %s%n", color.getColor());
                return;
            }
        }
        System.err.println("Niepoprawny numer koloru.");
    }

    /**
     * Dodanie akcesorium do smartfona na podstawie enumu SmartphoneAccesories.
     */
    private void chooseAccessory(Smartphone smartphone) {
        System.out.println("Dostępne akcesoria:");
        for (SmartphoneAccesories accessory : SmartphoneAccesories.values()) {
            System.out.printf("%d - %s (Cena: %.2f zł)%n", accessory.getId(), accessory.name(), accessory.getPrice());
        }
        System.out.print("Wybierz numer akcesorium: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        for (SmartphoneAccesories accessory : SmartphoneAccesories.values()) {
            if (accessory.getId() == choice) {
                smartphone.addAccessory(accessory);
                ProductConfiguration config = new ProductConfiguration("Accessory", accessory.name(), accessory.getPrice());
                smartphone.addConfiguration(config);

                System.out.printf("Dodano akcesorium: %s%n", accessory.name());
                return;
            }
        }
        System.err.println("Niepoprawny numer akcesorium.");
    }

    /**
     * Zmiana procesora w komputerze.
     */

    private void chooseProcessor(Computer computer) {
        System.out.println("Dostępne procesory:");
        for (ProcessorType processor : ProcessorType.values()) {
            System.out.printf("%d - %s (Cena: %.2f zł)%n", processor.getId(), processor.getName(), processor.getPrice());
        }
        System.out.print("Wybierz numer procesora: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            ProcessorType selectedProcessor = ProcessorType.fromId(choice);
            computer.setProcessor(selectedProcessor);
            ProductConfiguration config = new ProductConfiguration("Processor", selectedProcessor.getName(), selectedProcessor.getPrice());
            computer.addConfiguration(config);

            System.out.printf("Zmieniono procesor na: %s%n", selectedProcessor.getName());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Zmiana ilości RAM w komputerze.
     */
    private void chooseRam(Computer computer) {
        System.out.println("Dostępne konfiguracje RAM:");
        for (RamType ram : RamType.values()) {
            System.out.printf("%d - %s (Cena: %.2f zł)%n", ram.getId(), ram.getSize(), ram.getPrice());
        }
        System.out.print("Wybierz numer RAM: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        try {
            RamType selectedRam = RamType.fromId(choice);
            computer.setRam(selectedRam);
            ProductConfiguration config = new ProductConfiguration("RAM", selectedRam.getSize(), selectedRam.getPrice());
            computer.addConfiguration(config);

            System.out.printf("Zmieniono RAM na: %s%n", selectedRam.getSize());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }
}
