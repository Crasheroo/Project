package onlineshop.service;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Klasa zapewnia interfejs w konsoli do zarzadzania sklepem internetowym.
 */
public class CommandLineService {
    ProductManager productManager = new ProductManager();
    Cart cart = new Cart();
    Scanner scanner = new Scanner(System.in);
    OrderProcessor orderProcessor = new OrderProcessor();

    /**
     * Uruchamia aplikacje.
     */
    public void start() {
        productManager.fillDefaultData();
        handleUserMenu();
    }

    /**
     * Obsluguje menu uzytkownika przez wybor roznych opcji zarzadzania sklepem internetowym.
     */
    private void handleUserMenu() {
        boolean isRunning = true;

        while (isRunning) {
            try {
                System.out.println();
                System.out.println("1. Wyświetl dostępne produkty");
                System.out.println("2. Dodaj produkt do koszyka");
                System.out.println("3. Wyświetl koszyk");
                System.out.println("4. Usuń produkt z koszyka");
                System.out.println("5. Złóż zamówienie");
                System.out.println("6. Wyjdz");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        System.out.println("Dostepne produkty");
                        productManager.displayProducts();
                    }
                    case 2 -> chooseProduct();
                    case 3 -> {
                        System.out.println("Koszyk");
                        cart.displayCart();
                    }
                    case 4 -> removeProductFromCartOperation();
                    case 5 -> placeOrderOperation();
                    case 6 -> {
                        System.out.println("Baj baj");
                        isRunning = false;
                    }
                    default -> System.out.println("Wybierz inna opcje");
                }
            } catch (NumberFormatException e) {
                System.err.println("Wprowadz liczbe");
                scanner.nextLine();
            }
        }
    }

    /**
     * Dodaje produkt do koszyka na podstawie podanego ID produktu.
     */
    private void chooseProduct() {
        try {
            productManager.displayProducts();
            System.out.print("Podaj ID produktu, aby dodać go do koszyka: ");
            int productId = scanner.nextInt();
            scanner.nextLine();

            productManager.findProductById(productManager.getProducts(), productId).ifPresentOrElse(
                    product -> {
                        cart.addProductToCart(new CartItem(product, selectConfigurations(product), 1));
                        System.out.println("Dodano produkt do koszyka: " + product.getName());
                    },
                    () -> System.err.println("Nie ma produkru o podanym ID")
            );
        } catch (NumberFormatException e) {
            System.err.println("Wprowadź liczbę.");
        } catch (ProductOutOfStockException e) {
            System.err.println("Blad: " + e.getMessage());
        }
    }

    /**
     * Usuwa produkt z koszyka na podstawie podanego ID produktu.
     */
    private void removeProductFromCartOperation() {
        System.out.print("Podaj ID produktu, który chcesz usunąć: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        Optional<CartItem> optionalCartItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst();

        optionalCartItem.ifPresentOrElse(
                cartItem -> {
                    cartItem.getProduct().incrementStock(cartItem.getQuantity());
                    cart.removeProductFromCart(cartItem.getProduct());
                    System.out.println("Usunięto produkt z koszyka: " + cartItem.getProduct().getName());
                },
                () -> System.err.println("Nie ma produktu z tym ID w koszyku.")
        );
    }

    /**
     * Składa zamowienie na podstawie zawartosci koszyka z mozliwoscia wpisania kodu promocyjnego.
     */
    private void placeOrderOperation() {
        if (cart.getItems().isEmpty()) {
            System.err.println("Koszyk jest pusty");
            return;
        }
        System.out.print("Podaj imie i nazwisko: ");
        String name = scanner.nextLine();

        System.out.print("Podaj email: ");
        String email = scanner.nextLine();

        Order order = new Order(productManager.generateOrderId(), name, email, cart);

        AtomicBoolean discountApplied = new AtomicBoolean(false);
        while (!discountApplied.get()) {
            System.out.println("Wybierz opcje:");
            System.out.println("1 - Wyświetl kody promocyjne");
            System.out.println("2 - Podaj kod promocyjny");
            System.out.println("3 - Pomiń kod promocyjny");
            int choosenOption = scanner.nextInt();
            scanner.nextLine();

            switch (choosenOption) {
                case 1 -> productManager.displayDiscounts();
                case 2 -> applyDiscount(order, discountApplied);
                case 3 -> discountApplied.set(true);
                default -> System.err.println("Niepoprawna opcja");

            }

            cart.getItems().forEach(item -> item.getProduct().decrementStock(item.getQuantity()));

            orderProcessor.processOrder(order);
            cart.clearCart();
            System.out.println("Zamowienie zostalo zlozone.");
            exit();
        }
    }

    /**
     * Stosuje rabat do zamowienia, jesli kod promocyjny jest poprawny
     */
    private void applyDiscount(Order order, AtomicBoolean discountApplied) {
        System.out.print("Podaj kod promocyjny: ");
        String promoCode = scanner.nextLine();

        productManager.getDiscountByCode(promoCode)
                .ifPresentOrElse(
                        discount -> {
                            order.applyDiscount(discount);
                            System.out.println("Rabat: " + order.getDiscountValue() + " zł");
                            discountApplied.set(true);
                        },
                        () -> System.err.println("Nie ma takiego kodu")
                );
    }

    /**
     * Wybór konfiguracji z dostępnych opcji.
     */
    private List<ProductConfiguration> selectConfigurations(Product product) {
        List<ProductConfiguration> selectedConfigurations = new ArrayList<>();
        System.out.println("Konfiguracja produktu: " + product.getName());

        for (Type type : product.getConfigurations().stream().map(ProductConfiguration::getType).distinct().toList()) {
            List<ProductConfiguration> options = product.getConfigurations().stream()
                    .filter(config -> config.getType() == type)
                    .toList();

            System.out.printf("Dostępne opcje dla %s:\n", type.getName());
            for (int i = 0; i < options.size(); i++) {
                ProductConfiguration config = options.get(i);
                System.out.printf("%d. %s (+ %.2f zł)\n", i + 1, config.getValue(), config.getPrice());
            }

            int choice = -1;
            while (choice < 1 || choice > options.size()) {
                System.out.print("Wybierz opcję (1-" + options.size() + "): ");
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                    scanner.nextLine();
                } else {
                    System.err.println("Podaj liczbę!");
                    scanner.nextLine();
                }
            }

            selectedConfigurations.add(options.get(choice - 1));
        }

        return selectedConfigurations;
    }

    /**
     * Zamyka aplikacje.
     */
    private void exit() {
        System.out.println("Baj baj");
        System.exit(0);
    }
}
