package onlineshop.service;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
        product.getInfo();
        System.out.println("Produkt dodany: " + product);
    }

    public void removeProduct(int id) {
        boolean removed = products.removeIf(product -> product.getId() == id);
        if (!removed) {
            throw new ProductOutOfStockException("Produkt o ID " + id + " nie istnieje");
        }
        System.out.println("Produkt usunięty: ID " + id);
    }

    public void updateProduct(int id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId() == id) {
                products.set(i, product);
                System.out.println("Produkt zaaktualizowany: ID " + id);
                return;
            }
        }
        System.out.println("Produkt o ID " + id + " nie został odnaleziony.");
    }

    public void displayProducts() {
        if (products.isEmpty()) {
            System.out.println("Brak produktów.");
        } else {
            products.forEach(product -> product.getInfo());
        }
    }

    public List<Product> getProducts() {
        return products;
    }
}