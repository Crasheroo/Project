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
    private List<CartItem> items;

    public Cart() {
        this.items = new ArrayList<>();
    }


    /**
     * Dodaje produkt do koszyka.
     * Zmniejsza liczbe dostepnych sztuk produktu w magazynie o 1
     */
    public void addProductToCart(CartItem cartItem) {
        Optional<CartItem> existingItem = items.stream()
                .filter(item -> item.getProduct().equals(cartItem.getProduct()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem existing = existingItem.get();
            int newQuantity = existing.getQuantity() + cartItem.getQuantity();

            if (cartItem.getProduct().getStock() < newQuantity) {
                throw new ProductOutOfStockException("Brak wystarczającej ilości produktu w magazynie.");
            }

            existing.setQuantity(newQuantity);
        } else {
            items.add(cartItem);
        }

        cartItem.getProduct().decrementStock(cartItem.getQuantity());
    }

    /**
     * Usuwa produkt z koszuka.
     * Zwiększa liczbę dostępnych produktów w magazynie o 1
     */
    public void removeProductFromCart(Product product) {
        Optional<CartItem> optionalItem = items.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();

        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();
            product.incrementStock(item.getQuantity()); // Przywracamy ilość do magazynu
            items.remove(item);
            System.out.println("Usunięto produkt z koszyka: " + product.getName());
        } else {
            System.err.println("Produkt nie znajduje się w koszyku.");
        }
    }

    /**
     * Wyświetla zawartość koszyka w konsoli.
     */
    public void displayCart() {
        if (items.isEmpty()) {
            System.err.println("Koszyk jest pusty.");
        } else {
            items.forEach(item -> {
                System.out.println("Produkt: " + item.getProduct().getName());
                System.out.println("Ilość: " + item.getQuantity());
                System.out.println("Wybrane konfiguracje:");
                item.getConfigurations().forEach(config ->
                        System.out.printf("  - %s: %s (+ %.2f zł)\n", config.getType(), config.getValue(), config.getPrice()));
                double totalItemPrice = (item.getProduct().getPrice() +
                        item.getConfigurations().stream().mapToDouble(ProductConfiguration::getPrice).sum()) * item.getQuantity();
                System.out.printf("Łączna cena: %.2f zł\n\n", totalItemPrice);
            });
        }
    }

    /**
     * Oprocznia koszyk.
     */
    public void clearCart() {
        items.forEach(item -> item.getProduct().incrementStock(item.getQuantity()));
        items.clear();
        System.out.println("Koszyk zostal wyczyszczony");
    }

    public List<CartItem> getItems() {
        return items;
    }
}
