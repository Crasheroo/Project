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
        List<ProductConfiguration> computerConfigurations = List.of(
                new ProductConfiguration(Type.PROCESSOR, "Intel i5", 0),
                new ProductConfiguration(Type.PROCESSOR, "Intel i7", 800),
                new ProductConfiguration(Type.PROCESSOR, "Intel i9", 1200),
                new ProductConfiguration(Type.RAM_SIZE, "8GB", 0),
                new ProductConfiguration(Type.RAM_SIZE, "16GB", 500),
                new ProductConfiguration(Type.RAM_SIZE, "32GB", 1000)
        );

        List<ProductConfiguration> smartphoneConfigurations = List.of(
                new ProductConfiguration(Type.COLOR, "Czarny", 0),
                new ProductConfiguration(Type.COLOR, "Niebieski", 150),
                new ProductConfiguration(Type.COLOR, "Zielony", 150),
                new ProductConfiguration(Type.BATTERY_CAPACITY, "2200mAh", 0),
                new ProductConfiguration(Type.BATTERY_CAPACITY, "3200mAh", 400),
                new ProductConfiguration(Type.BATTERY_CAPACITY, "4200mAh", 800),
                new ProductConfiguration(Type.ACCESSORY, "Słuchawki", 150),
                new ProductConfiguration(Type.ACCESSORY, "Szybka ładowarka", 50),
                new ProductConfiguration(Type.ACCESSORY, "Etui", 30),
                new ProductConfiguration(Type.ACCESSORY, "Uchwyt", 45)
        );

        List<ProductConfiguration> emptyConfiguration = new ArrayList<>();

        products.add(new Product(1, "Computer", "Gaming PC", 3000.00, 10, computerConfigurations));
        products.add(new Product(2, "Smartphone", "Smartphone Galaxy", 2000.00, 15, smartphoneConfigurations));
        products.add(new Product(3, "Electronics", "Wireless Mouse", 25.99, 50, emptyConfiguration));
        products.add(new Product(4, "Electronics", "Bluetooth Keyboard", 45.99, 30, emptyConfiguration));
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
}