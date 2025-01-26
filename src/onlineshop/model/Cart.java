package onlineshop.model;

import onlineshop.exceptions.ProductOutOfStockException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    * Dodaje produkt do koszyka.
    * Zmniejsza liczbe dostepnych sztuk produktu w magazynie o 1
    */
    public synchronized void addProductToCart(Product product) {
        if (product.getItemsAvailable() <= 0) {
            throw new ProductOutOfStockException("Produktu " + product.getName() + " nie ma w magazynie");
        }
        products.add(product);
        product.setItemsAvailable(product.getItemsAvailable() - 1);
        System.out.println("Dodano do koszyka: " + product.getName());
    }

    /**
     * Usuwa produkt z koszuka.
     * Zwiększa liczbę dostępnych produktów w magazynie o 1
     */
    public synchronized void removeProductFromCart(Product product) {
        if (products.remove(product)) {
            product.setItemsAvailable(product.getItemsAvailable() + 1);
            System.out.println("Usnieto z koszyka: " + product.getName());
        } else {
            throw new ProductOutOfStockException("Produktu nie ma w koszyku");
        }
    }

    /**
     * Wyświetla zawartość koszyka w konsoli.
     */
    public synchronized void displayCart() {
        if (products.isEmpty()) {
            System.err.println("Koszyk jest pusty.");
        } else {
            System.out.println("Produkty w koszyku:");
            for (Product product : products) {
                System.out.println(product + ", calkowita cena: " + product.getTotalPrice() + " zł");
            }
        }
    }

    /**
     * Dodaje akcesoria do smartfona znajdujacego sie w koszyku.
     */
    public void updateCartAccesories(int productId, List<String> accesories) {
        Optional<Product> productOptional = products.stream()
                .filter(p -> p.getId() == productId)
                .findFirst();

        if (productOptional.isPresent() ) {
            Smartphone smartphone = (Smartphone) productOptional.get();
            smartphone.getAccesories().addAll(accesories);
            System.out.println("Dodano akcesoria dla " + smartphone.getName());
        } else {
            System.err.println("Nie masz telefonu w koszyku.");
        }
    }

    /**
     * Konfiguruje komputer znajdujący się w koszyku, zmieniając jego procesor i ilość RAM.
     *
     */
    public void updateComputer(int productId, String newProcessor, int newRam) {
        Optional<Product> productOptional = products.stream()
                .filter(p -> p.getId() == productId)
                .findFirst();

        if (productOptional.isPresent()) {
            Computer computer = (Computer) productOptional.get();
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
    public synchronized void clearCart() {
        products.forEach(product -> product.setItemsAvailable(product.getItemsAvailable() + 1));
        products.clear();
        System.out.println("Koszyk zostal zresetowany");
    }

    public List<Product> getProducts() {
        return products;
    }
}
