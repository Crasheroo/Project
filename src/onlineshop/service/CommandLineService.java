package onlineshop.service;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;

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
    OrderProcessor orderProcessor = new OrderProcessor(5);
    OrderRepository orderRepository = new OrderRepository();
    ProductConfigurationService productConfigurationService = new ProductConfigurationService(scanner);
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
        while (true) {
            try {
                System.out.println();
                System.out.println("1. Wyświetl dostępne produkty");
                System.out.println("2. Dodaj produkt do koszyka");
                System.out.println("3. Wyświetl koszyk");
                System.out.println("4. Usuń produkt z koszyka");
                System.out.println("5. Złóż zamówienie");
                System.out.println("6. Konfiguruj produkt z koszyka");
                System.out.println("7. Wyjdz");

                int option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        System.out.println("Dostepne produkty");
                        productManager.displayProducts();
                    }
                    case 2 -> addProductToCart();
                    case 3 -> {
                        System.out.println("Koszyk");
                        cart.displayCart();
                    }
                    case 4 -> removeProductFromCart();
                    case 5 -> placeOrder();
                    case 6 -> configureProduct();
                    case 7 -> exit();
                    default -> System.out.println("Wybierz inna opcje");
                }
            } catch (NumberFormatException e) {
                System.err.println("Wprowadz liczbe");
            }
        }
    }

    /**
     * Dodaje produkt do koszyka na podstawie podanego ID produktu.
     */
    private void addProductToCart() {
        try {
            productManager.displayProducts();
            System.out.print("Podaj ID produktu zeby dodac go do koszyka: ");
            int productId = scanner.nextInt();
            scanner.nextLine();

            Optional<Product> optionalProduct = productManager.findProductById(productManager.getProducts(), productId);

            optionalProduct.ifPresentOrElse(
                    product -> {
                        try {
                            cart.addProductToCart(product);
                        } catch (ProductOutOfStockException e) {
                            System.err.println("Błąd: " + e.getMessage());
                        }
                    }, () -> System.err.println("Nie ma produktu z tym ID")
            );
        } catch (NumberFormatException e) {
            System.err.println("Wprowadz liczbe");
        }
    }

    /**
     * Usuwa produkt z koszyka na podstawie podanego ID produktu.
     */
    private void removeProductFromCart() {
        System.out.print("Podaj Id produktu ktory chcesz usunąć: ");
        int productId = scanner.nextInt();
        scanner.nextLine();

        Optional<Product> optionalProduct = productManager.findProductById(cart.getProducts(), productId);

        optionalProduct.ifPresentOrElse(
                product -> cart.removeProductFromCart(product),
                () -> System.err.println("Nie ma produktu z tym ID")
        );
    }

    /**
     * Składa zamowienie na podstawie zawartosci koszyka z mozliwoscia wpisania kodu promocyjnego.
     */
    private void placeOrder() {
        if (cart.getProducts().isEmpty()) {
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

            orderProcessor.processOrderAsync(order);
            orderRepository.saveOrder(order);
            cart.clearCart();
            System.out.println("Zamowienie zostalo zlozone.");
            exit();
        }
    }

    /**
     * Umozliwia konfiguracje wybranego produktu w koszyku.
     */
    private void configureProduct() {
        System.out.println("Podaj ID produktu");
        int productId = scanner.nextInt();
        scanner.nextLine();

        Optional<Product> optionalProduct = productManager.findProductById(cart.getProducts(), productId);
        optionalProduct.ifPresentOrElse(
                product -> productConfigurationService.configureProduct(product, cart),
                () -> System.err.println("Nie ma produktu z tym ID")
        );
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
     * Zamyka aplikacje.
     */
    private void exit() {
        System.out.println("Baj baj");
        scanner.close();
        orderProcessor.shutdown();
        System.exit(0);
    }
}
