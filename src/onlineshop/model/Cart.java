package onlineshop.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        System.out.println("Dodano do koszyka: " + product.getName());
    }

    public void removeProduct(Product product) {
        products.remove(product);
        System.out.println("Usunięto z koszyka: " + product.getName());
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
