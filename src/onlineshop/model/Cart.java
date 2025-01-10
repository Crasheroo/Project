package onlineshop.model;

import onlineshop.exceptions.ProductOutOfStockException;

import java.util.ArrayList;
import java.util.List;

/**
 * klasa reprezentująca koszyk w sklepie internetowym.
 * Pozwala na dodawanie, usuwanie i wyświetlanie produktów,
 * zarządzanie akcesoriami do telefonów, konfiguracje komputerów
 * i obliczanie całkowitej wartości rzeczy w koszyku.
 */

public class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    /**
    *  Dodaje produkt do koszyka.
    *  Zmniejsza liczbe dostepnych sztuk produktu w magazynie o 1
    */
    public void addProduct(Product product) {
        if (product.getAmountOfAvailable() <= 0) {
            throw new ProductOutOfStockException("Produktu " + product.getName() + " nie ma w magazynie");
        }
        products.add(product);
        product.setAmountOfAvailable(product.getAmountOfAvailable() - 1);
        System.out.println("Dodano do koszyka: " + product.getName());
    }

    /**
     * Usuwa produkt z koszuka.
     * Zwiększa liczbę dostępnych produktów w magazynie o 1
     */
    public void removeProduct(Product product) {
        if (products.remove(product)) {
            product.setAmountOfAvailable(product.getAmountOfAvailable() + 1);
            System.out.println("Usnieto z koszyka: " + product.getName());
        } else {
            throw new ProductOutOfStockException("Produktu nie ma w koszyku");
        }
    }

    /**
     * Wyświetla zawartość koszyka w konsoli.
     */
    public void displayCart() {
        if (products.isEmpty()) {
            System.err.println("Koszyk jest pusty.");
        } else {
            System.out.println("Produkty w koszyku:");
            products.forEach(System.out::println);
        }
    }

    /**
     * Oblicza łączną wartość produktów w koszyku.
     *
     */
    public double calculateTotal() {
        return products.stream()
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    /**
     * Dodaje akcesoria do smartfona znajdujacego sie w koszyku.
     */
    public void updateCartAccesories(int productId, List<String> accesories) {
        Product product = products.stream()
                .filter(p -> p.getId() == productId && p instanceof Smartphone)
                .findFirst()
                .orElse(null);

        if (product instanceof Smartphone smartphone) {
            smartphone.getAccesories().addAll(accesories);
            System.out.println("Dodano akcesoria dla " + smartphone.getName());
        } else {
            System.err.println("Nie masz telefonu w koszyku");
        }
    }

    /**
     * Konfiguruje komputer znajdujący się w koszyku, zmieniając jego procesor i ilość RAM.
     *
     */
    public void updateComputer(int productId, String newProcessor, int newRam) {
        Product product = products.stream()
                .filter(p -> p.getId() == productId && p instanceof Computer)
                .findFirst()
                .orElse(null);

        if (product instanceof Computer computer) {
            computer.setProcessor(newProcessor);
            computer.setAmountOfRam(newRam);
            System.out.println("Komputer " + computer.getName() + " zostal skonfigurowany");
        } else {
            System.err.println("Nie znaleziono komputera z podanym id w koszyku");
        }
    }

    /**
     * Oprocznia koszyk.
     */
    public void clearCart() {
        products.clear();
        System.out.println("Koszyk zostal zresetowany");
    }

    public List<Product> getProducts() {
        return products;
    }
}
