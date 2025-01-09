package onlineshop.model;

import onlineshop.exceptions.ProductOutOfStockException;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product.getAmountOfAvailable() <= 0) {
            throw new ProductOutOfStockException("Produktu " + product.getName() + " nie ma w magazynie");
        }
        products.add(product);
        product.setAmountOfAvailable(product.getAmountOfAvailable() - 1);
        System.out.println("Dodano do koszyka: " + product.getName());
    }

    public void removeProduct(Product product) {
        if (products.remove(product)) {
            product.setAmountOfAvailable(product.getAmountOfAvailable() + 1);
            System.out.println("Usnieto z koszyka: " + product.getName());
        } else {
            throw new ProductOutOfStockException("Produktu nie ma w koszyku");
        }
    }

    public void displayCart() {
        if (products.isEmpty()) {
            System.out.println("Koszyk jest pusty.");
        } else {
            System.out.println("Produkty w koszyku:");
            products.forEach(product -> product.getInfo());
        }
    }

    public double calculateTotal() {
        return products.stream()
                .mapToDouble(p -> p.getPrice())
                .sum();
    }

    public void clearCart() {
        products.clear();
        System.out.println("Koszyk zostal zresetowany");
    }

    public List<Product> getProducts() {
        return products;
    }
}
