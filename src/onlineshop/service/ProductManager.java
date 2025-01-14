package onlineshop.service;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;

import java.util.*;

public class ProductManager {
    private final List<Product> products = new ArrayList<>();
    private final Map<String, Discount> discountMap = new HashMap<>();

    public ProductManager() {
        initializeDiscounts();
    }

    public void fillDefaultData() {
        addProduct(new Computer(1, "Laptop gejmingowy", 10000, 2, "", 6));
        addProduct(new Smartphone(2, "Samsong", 3000, 10, "", 2137));
        addProduct(new Electronics(3, "Telewizor", 1200, 50));
    }

    public void addProduct(Product product) {
        products.add(product);
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
            products.forEach(System.out::println);
        }
    }

    private void initializeDiscounts() {
        discountMap.put("BOBI10", new Discount("BOBI10", 0.1, 0, 0));
        discountMap.put("FIXED50", new Discount("FIXED50", 0, 50, 200));
    }

    public void displayDiscounts() {
        if (discountMap.isEmpty()) {
            System.out.println("Brak kodow rabatowych");
        } else {
            System.out.println("Dostępne kody promocyje:");
            discountMap.forEach((code, discount) -> {
                System.out.println("Kod: " + code);
                System.out.println("Rabat procentowy: " + (discount.getPercentage() * 100) + "%");
                System.out.println("Rabat stały: " + discount.getFixedAmount() + " zł");
                System.out.println("Minimalna wartość zamówienia: " + discount.getMinimumOrderValue() + " zł");
                System.out.println();
            });
        }
    }

    public Optional<Discount> getDiscountByCode(String promoCode) {
        return Optional.ofNullable(discountMap.get(promoCode.toUpperCase()));
    }

    public Optional<Product> findProductById(List<Product> products, int productId) {
        return products.stream()
                .filter(product -> product.getId() == productId)
                .findFirst();
    }

    public int generateOrderId() {
        return (int) (Math.random() * 1000);
    }

    public List<Product> getProducts() {
        return products;
    }

    public Map<String, Discount> getDiscountMap() {
        return discountMap;
    }
}