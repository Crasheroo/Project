package onlineshop.service;

import onlineshop.model.*;

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

    /**
     * Funckja do usuwania akcesoriow poprzez id o ile sa one dostepne.
     */
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

    /**
     * Funkcja dodawania akcesoriow poprzez ich id
     */
    private void addAccessory(Smartphone smartphone) {
        System.out.println("\nDostepne akcesoria:");
        for (SmartphoneAccesories accessory : SmartphoneAccesories.values()) {
            System.out.println(accessory.getId() + " - " + accessory.name() + " (Cena: " + accessory.getPrice() + " zł)");
        }

        System.out.print("Wybierz akcesorium (numer): ");
        int accesoryOption = scanner.nextInt();
        scanner.nextLine();

        for (SmartphoneAccesories accessory : SmartphoneAccesories.values()) {
            if (accessory.getId() == accesoryOption) {
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

    /**
     * Funkcja dodawania koloru telefonu poprzez nazwe
     */
    private void selectColor(Smartphone smartphone) {
        System.out.println("Wybierz kolor smartfona:");
        for (SmartphoneColors color : SmartphoneColors.values()) {
            System.out.println(color.getId() + " - " + color.getColor());
        }

        System.out.print("Wybierz opcję: ");
        int colorOption = scanner.nextInt();
        scanner.nextLine();

        for (SmartphoneColors color : SmartphoneColors.values()) {
            if (color.getId() == colorOption) {
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
        System.out.println("Konfigurowanie komputera.");
        while (true) {
            System.out.println("1 - Zmien procesor");
            System.out.println("2 - Zmien ilosc RAM");
            System.out.println("3 - Wyswietl konfiguracje");
            System.out.println("0 - Wyjdz");
            System.out.print("Wybierz opcje: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0 -> {
                    System.out.println("Koniec konfiguracji");
                    cart.updateComputer(computer.getId(), computer.getProcessor(), computer.getAmountOfRam());
                    return;
                }
                case 1 -> changeProcessor(computer);
                case 2 -> changeRam(computer);
                case 3 -> displayConfiguration(computer);
                default -> System.err.println("Niepoprawna opcja");
            }
        }
    }

    private void displayConfiguration(Product product) {
        if (product instanceof Computer computer) {
            System.out.println("\nAktualna konfiguracja komputera:");
            System.out.println("Procesor: " + computer.getProcessor());
            System.out.println("RAM: " + computer.getAmountOfRam() + " GB");
        } else if (product instanceof Smartphone smartphone) {
            System.out.println("\nAktualna konfiguracja:");
            System.out.println("Kolor: " + (smartphone.getColor() != null ? smartphone.getColor() : "Nie wybrano"));
            System.out.println("Akcesoria: " + (smartphone.getAccesories().isEmpty() ? "Brak" : smartphone.getAccesories()));
        } else {
            System.err.println("Nieznany typ produktu");
        }
    }

    private void changeProcessor(Computer computer) {
        System.out.print("Podaj nowy procesor: ");
        String newProcessor = scanner.nextLine();
        computer.setProcessor(newProcessor);
        System.out.println("Procesor zostal zmienony na: " + newProcessor);
    }

    private void changeRam(Computer computer) {
        System.out.print("Podaj nową ilosc RAM (GB): ");
        int newRam = scanner.nextInt();
        scanner.nextLine();
        computer.setAmountOfRam(newRam);
        System.out.println("RAM zostal zmieniony na: " + newRam + " GB");
    }

    /**
     * Tu rozpoczyna proces konfiguracji dla podanego produktu.
     */
//    public void configureProduct(Product product, Cart cart) {
//        if (product instanceof Smartphone smartphone) {
//            configureSmartphone(smartphone, cart);
//        } else if (product instanceof Computer computer) {
//            configureComputer(computer, cart);
//        } else {
//            System.err.println("Nie ma konfiguracji dla tego typu produktu");
//        }
//    }

    public void configureProduct(Product product) {
        List<ProductConfiguration> availableConfigurations = ConfigurationStore.getConfigurations(product.getType());
        if (availableConfigurations.isEmpty()) {
            System.err.println("Brak dostepnych konfiguracji dla typu produktu: " + product.getType());
            return;
        }

        while (true) {
            System.out.println("Konfigurowanie produktu: " + product.getName());
            System.out.println("1 - Wybierz konfiguracje");
            System.out.println("2 - Wyswietl aktualne konfiguracje");
            System.out.println("0 - Wyjdź");
            System.out.print("Wybierz opcję: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 0 -> {
                    System.out.println("Zakonczono konfiguracje");
                    return;
                }
                case 1 -> chooseConfiguration(product, availableConfigurations);
                case 2 -> product.displayConfigurations();
                default -> System.err.println("Niepoprawna opcja");
            }
        }
    }

    private void chooseConfiguration(Product product, List<ProductConfiguration> availableConfigurations) {
        System.out.println("Dostepne konfiguracje:");
        for (int i = 0; i < availableConfigurations.size(); i++) {
            ProductConfiguration config = availableConfigurations.get(i);
            System.out.println((i + 1) + " - " + config);
        }

        System.out.print("Wybierz numer konfiguracji: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice > 0 && choice <= availableConfigurations.size()) {
            ProductConfiguration selectedConfig = availableConfigurations.get(choice - 1);
            product.addConfiguration(selectedConfig);
            System.out.println("Dodano konfiguracje: " + selectedConfig);
        } else {
            System.err.println("Niepoprawny numer");
        }
    }


}
