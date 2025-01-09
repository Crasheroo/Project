package onlineshop;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;
import onlineshop.service.ProductManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ProductManager productManager = new ProductManager();
        Cart cart = new Cart();
        Scanner scanner = new Scanner(System.in);

        productManager.addProduct(new Computer(1, "Laptop gejmingowy", 10000, 2, "", 6));
        productManager.addProduct(new Smartphone(2, "Samsong", 3000, 10, "", 2137));
        productManager.addProduct(new Electronics(3, "Telewizor", 1200, 50));

        while (true){
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

                case 2 -> {
                    System.out.print("Podaj ID produktu zeby dodac go do koszyka: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine();

                    try {
                        Product product = findProductById(productManager.getProducts(), productId);
                        if (product != null) {
                            cart.addProduct(product);
                        } else {
                            System.err.println("Nie ma produktu z tym ID");
                        }
                    } catch (ProductOutOfStockException e) {
                        System.err.println("Błąd: " + e.getMessage());
                    }
                }

                case 3 -> {
                    System.out.println("Koszyk");
                    cart.displayCart();
                }

                case 4 -> {
                    System.out.print("Podaj Id produktu ktory chcesz usunąć: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine();

                    Product product = findProductById(cart.getProducts(), productId);
                    if (product != null) {
                        cart.removeProduct(product);
                    } else {
                        System.err.println("Nie ma produktu z tym ID");
                    }
                }

                case 5 -> {
                    if (cart.getProducts().isEmpty()) {
                        System.err.println("Koszyk jest pusty");
                    } else {
                        System.out.print("Podaj imie i nazwisko: ");
                        String name = scanner.nextLine();

                        System.out.print("Podaj email: ");
                        String email = scanner.nextLine();

                        Order order = new Order(generateOrderId(), name, email, new ArrayList<>(cart.getProducts()));

                        System.out.print("Podaj kod promocyjny jezeli jest: ");
                        String promoCode = scanner.nextLine();
                        Discount discount = getDiscountByCode(promoCode);
                        if (discount != null) {
                            order.applyDiscount(discount);
                            System.out.println("Rabat: " + order.getDiscountValue() + " zł");
                        } else if (!promoCode.isEmpty()) {
                            System.out.println("Nie ma takiego kodu");
                        }

                        order.displayOrderDetails();

                        cart.clearCart();
                    }
                }

                case 6 -> {
                    System.out.println("Baj baj");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Wybierz inna opcje");
            }
        }
    }

    private static Discount getDiscountByCode(String promoCode) {
        return switch (promoCode.toUpperCase()) {
            case "BOBI10" -> new Discount("BOBI10", 0.1, 0, 0);
            case "FIXED50" -> new Discount("FIXED50", 0, 50, 200);
            default -> null;
        };
    }

    private static Product findProductById(List<Product> products, int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElse(null);
    }

    private static int generateOrderId() {
        return (int) (Math.random() * 1000);
    }
}