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
            System.err.println("Koszyk jest pusty.");
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

    public void clearCart() {
        products.clear();
        System.out.println("Koszyk zostal zresetowany");
    }

    public List<Product> getProducts() {
        return products;
    }
}
