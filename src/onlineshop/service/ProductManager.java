package onlineshop.service;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProductManager {
    private final List<Product> products = new ArrayList<>();
    private final Map<String, Discount> discountMap = new HashMap<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    public ProductManager() {
        initializeDiscounts();
    }

    public void fillDefaultData() {
        addProductAsync(new Computer(1, "Laptop gejmingowy", 10000, 2, ProcessorType.INTEL_I7, RamType.RAM_8GB));
        addProductAsync(new Computer(2, "Ultrabook biznesowy", 8000, 3, ProcessorType.INTEL_I5, RamType.RAM_16GB));

        addProductAsync(new Smartphone(3, "Samsong Galaxy", 3000, 10, SmartphoneColors.black, 4500));
        addProductAsync(new Smartphone(4, "iPhone 15", 7000, 5, SmartphoneColors.white, 5000));

        addProductAsync(new Electronics(5, "Telewizor 4K", 1200, 50));
        addProductAsync(new Electronics(6, "Kamera internetowa", 250, 30));
    }

    public void addProductAsync(Product product) {
        executorService.submit(() -> {
            products.add(product);
            System.out.println("Produkt dodany: " + product);
        });
    }

    public void removeProductAsync(int id) {
        executorService.submit(() -> {
            boolean removed = products.removeIf(product -> product.getId() == id);
            if (!removed) {
                throw new ProductOutOfStockException("Produkt o ID " + id + " nie istnieje");
            }
            System.out.println("Produkt usunięty: ID " + id);
        });
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

    public void addProductConfiguration(int productId, ProductConfiguration config) {
        Optional<Product> optionalProduct = findProductById(products, productId);
        optionalProduct.ifPresentOrElse(
                product -> {
                    product.addConfiguration(config);
                    System.out.println("Dodano konfiguracje do produktu: " + product.getName());
                },
                () -> System.err.println("Nie znaleziono produktu o ID: " + productId)
        );
    }

    public void displayProductConfigurations(int productId) {
        Optional<Product> optionalProduct = findProductById(products, productId);
        optionalProduct.ifPresentOrElse(
                Product::displayConfigurations,
                () -> System.err.println("Nie znaleziono produktu o ID: " + productId)
        );
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