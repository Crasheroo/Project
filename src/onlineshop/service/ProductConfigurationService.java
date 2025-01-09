package onlineshop.service;

import onlineshop.model.Cart;
import onlineshop.model.Computer;
import onlineshop.model.Product;
import onlineshop.model.Smartphone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductConfigurationService {
    private final Scanner scanner;

    public ProductConfigurationService(Scanner scanner) {
        this.scanner = scanner;
    }

    private void configureSmartphone(Smartphone smartphone, Cart cart) {
        List<String> newAccesories = new ArrayList<>();
        System.out.println("Dodawanie akcesoriow. Wybierz opcje:");
        System.out.println("1 - Dodaj akcesorium");
        System.out.println("0 - Wyjdz");

        while (true) {
            System.out.print("Wybierz opcję: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            if (option == 0) {
                break;
            } else if (option == 1) {
                System.out.print("Dodaj akcesorium: ");
                String accesory = scanner.nextLine();
                newAccesories.add(accesory);
            } else {
                System.err.println("Niepoprawna opcja");
            }
        }
        cart.updateCartAccesories(smartphone.getId(), newAccesories);
    }

    private void configureComputer(Computer computer, Cart cart) {
        System.out.print("Podaj nowy procesor: ");
        String newProcessor = scanner.nextLine();

        System.out.print("Podaj nową ilosc RAM (GB): ");
        int newRam = scanner.nextInt();
        scanner.nextLine();

        cart.updateComputer(computer.getId(), newProcessor, newRam);
    }

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
