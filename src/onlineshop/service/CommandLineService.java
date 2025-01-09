package onlineshop.service;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandLineService {
    ProductManager productManager = new ProductManager();
    Cart cart = new Cart();
    Scanner scanner = new Scanner(System.in);
    OrderProcessor orderProcessor = new OrderProcessor(5);
    OrderPersistance orderPersistance = new OrderPersistance();
    ProductConfigurationService productConfigurationService = new ProductConfigurationService(scanner);

    public void start() {
        productManager.fillDefaultData();
        handleUserMenu();
    }

    private void handleUserMenu() {
        while (true) {
            System.out.println();
            System.out.println("1. Wyświetl dostępne produkty");
            System.out.println("2. Dodaj produkt do koszyka");
            System.out.println("3. Wyświetl koszyk");
            System.out.println("4. Usuń produkt z koszyka");
            System.out.println("5. Złóż zamówienie");
            System.out.println("6. Dodaj akcesoria do telefonu");
            System.out.println("7. Konfiguruj komputer");
            System.out.println("8. Wyjdz");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> {
                    System.out.println("Dostepne produkty");
                    productManager.displayProducts();
                }

                case 2 -> {
                    productManager.displayProducts();
                    System.out.print("Podaj ID produktu zeby dodac go do koszyka: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine();

                    Optional<Product> optionalProduct = productManager.findProductById(productManager.getProducts(), productId);

                    optionalProduct.ifPresentOrElse(
                            product -> {
                                try {
                                    cart.addProduct(product);
                                } catch (ProductOutOfStockException e) {
                                    System.err.println("Błąd: " + e.getMessage());
                                }
                            }, () -> System.err.println("Nie ma produktu z tym ID")
                    );
                }

                case 3 -> {
                    System.out.println("Koszyk");
                    cart.displayCart();
                }

                case 4 -> {
                    System.out.print("Podaj Id produktu ktory chcesz usunąć: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine();

                    Optional<Product> optionalProduct = productManager.findProductById(cart.getProducts(), productId);

                    optionalProduct.ifPresentOrElse(
                            product -> cart.removeProduct(product),
                            () -> System.err.println("Nie ma produktu z tym ID")
                    );
                }

                case 5 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.err.println("Koszyk jest pusty");
                        return;
                    }
                    System.out.print("Podaj imie i nazwisko: ");
                    String name = scanner.nextLine();

                    System.out.print("Podaj email: ");
                    String email = scanner.nextLine();

                    Order order = Order.fromCart(productManager.generateOrderId(), name, email, cart);

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

                            case 2 -> {
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

                            case 3 -> {
                                System.out.println("Pominieto kod promocyjny");
                                discountApplied.set(true);
                            }

                            default -> System.err.println("Niepoprawna opcja");

                        }

                        orderProcessor.processOrder(order);
                        orderPersistance.saveOrder(order);
                        cart.clearCart();
                        System.out.println("Zamowienie zostalo zlozone.");
                    }
                }

                case 6, 7 -> {
                    System.out.println("Podaj ID produktu");
                    int productId = scanner.nextInt();
                    scanner.nextLine();

                    Optional<Product> optionalProduct = productManager.findProductById(cart.getProducts(), productId);
                    optionalProduct.ifPresentOrElse(
                            product -> productConfigurationService.configureProduct(product, cart),
                            () -> System.err.println("Nie ma produktu z tym ID")
                    );
                }

                case 8 -> {
                    System.out.println("Baj baj");
                    scanner.close();
                    orderProcessor.shutdownThread();
                    return;
                }

                default -> System.out.println("Wybierz inna opcje");
            }
        }
    }
}
