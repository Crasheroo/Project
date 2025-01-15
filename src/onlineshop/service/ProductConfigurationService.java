package onlineshop.service;

import onlineshop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Klasa ma funkcjonalnosc konfiguracji produktow znajdujących sie w koszyku przez interakcje poprzez konsole.
 */
public class ProductConfigurationService {
    private final Scanner scanner;

    public ProductConfigurationService(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Dodaje akcesoria dla smartfona znajdujacego sie w koszyku poprzez konsole.
     */
    private void configureSmartphone(Smartphone smartphone, Cart cart) {
        System.out.println("Konfigurowanie smartfona. Wybierz opcje:");
        while (true) {
            System.out.println("1 - Dodaj akcesorium");
            System.out.println("2 - Dodaj kolor");
            System.out.println("3 - Wyswietl konfiguracje");
            System.out.println("4 - Usun akcesorium");
            System.out.println("0 - Wyjdz");
            System.out.print("Wybierz opcje: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0 -> {
                    cart.updateCartAccesories(smartphone.getId(), smartphone.getAccesories());
                    System.out.println("Koniec konfiguracji");
                    return;
                }
                case 1 -> addAccessory(smartphone);
                case 2 -> selectColor(smartphone);
                case 3 -> displayConfiguration(smartphone);
                case 4 -> removeAccessory(smartphone);
                default -> System.err.println("Zla opcja");
            }
        }
    }

    private void removeAccessory(Smartphone smartphone) {
        if (smartphone.getAccesories().isEmpty()) {
            System.out.println("Brak akcesoriow do usuniecia");
            return;
        }

        System.out.println("\nWybrane akcesoria:");
        int i = 1;
        for (String accesory : smartphone.getAccesories()) {
            System.out.println(i + " - " + accesory);
            i++;
        }

        System.out.print("Wybierz numer akcesorium do usuniecia: ");
        int removeOption = scanner.nextInt();
        scanner.nextLine();

        if (removeOption > 0 && removeOption <= smartphone.getAccesories().size()) {
            String removed = smartphone.getAccesories().remove(removeOption - 1);
            System.out.println("Usunieto akcesorium: " + removed);
        } else {
            System.err.println("Niepoprawny numer");
        }
    }

    private void displayConfiguration(Smartphone smartphone) {
        System.out.println("\nAktualna konfiguracja:");
        System.out.println("Kolor: " + (smartphone.getColor() != null ? smartphone.getColor() : "Nie wybrano"));
        System.out.println("Akcesoria: " + (smartphone.getAccesories().isEmpty() ? "Brak" : smartphone.getAccesories()));
    }

    private void addAccessory(Smartphone smartphone) {
        System.out.println("\nDostepne akcesoria:");
        for (SmartphoneAccesories accessory : SmartphoneAccesories.values()) {
            System.out.println(accessory.getOption() + " - " + accessory.name() + " (Cena: " + accessory.getPrice() + " zł)");
        }

        System.out.print("Wybierz akcesorium (numer): ");
        int accesoryOption = scanner.nextInt();
        scanner.nextLine();

        for (SmartphoneAccesories accessory : SmartphoneAccesories.values()) {
            if (accessory.getOption() == accesoryOption) {
                if (smartphone.getAccesories().contains(accessory.name())) {
                    System.out.println("Juz bylo dodane");
                } else {
                    smartphone.addAcceossory(accessory.name());
                    System.out.println("Dodano akcesorium: " + accessory.name());
                }
                return;
            }
        }
        System.err.println("Niepoprawna opcja");
    }

    private void selectColor(Smartphone smartphone) {
        System.out.println("Wybierz kolor smartfona:");
        for (SmartphoneColors color : SmartphoneColors.values()) {
            System.out.println(color.getOption() + " - " + color.getColor());
        }

        System.out.print("Wybierz opcję: ");
        int colorOption = scanner.nextInt();
        scanner.nextLine();

        for (SmartphoneColors color : SmartphoneColors.values()) {
            if (color.getOption() == colorOption) {
                smartphone.setColor(color.getColor());
                System.out.println("Wybrano kolor: " + color.getColor());
                return;
            }
        }
    }

    /**
     * Konfiguruje komputer znajdujacy sie w koszuku.
     */
    private void configureComputer(Computer computer, Cart cart) {
        System.out.print("Podaj nowy procesor: ");
        String newProcessor = scanner.nextLine();

        System.out.print("Podaj nową ilosc RAM (GB): ");
        int newRam = scanner.nextInt();
        scanner.nextLine();

        cart.updateComputer(computer.getId(), newProcessor, newRam);
    }

    /**
     * Tu rozpoczyna proces konfiguracji dla podanego produktu.
     */
    public void configureProduct(Product product, Cart cart) {
        if (product instanceof Smartphone smartphone) {
            configureSmartphone(smartphone, cart);
        } else if (product instanceof Computer computer) {
            configureComputer(computer, cart);
        } else {
            System.err.println("Nie ma konfiguracji dla tego typu produktu");
        }
    }
}
