package onlineshop.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationStore {
    private static final Map<String, List<ProductConfiguration>> configurationsByType = new HashMap<>();

    static {
        configurationsByType.put("Smartphone", List.of(
                new ProductConfiguration("Kolor", "Czarny", 0),
                new ProductConfiguration("Kolor", "Biały", 0),
                new ProductConfiguration("Pojemność baterii", "4500 mAh", 100),
                new ProductConfiguration("Pojemność baterii", "5000 mAh", 150),
                new ProductConfiguration("Akcesoria", "Słuchawki", 150),
                new ProductConfiguration("Akcesoria", "Ładowarka", 100)
        ));
        configurationsByType.put("Computer", List.of(
                new ProductConfiguration("Procesor", "Intel i5", 0),
                new ProductConfiguration("Procesor", "Intel i7", 300),
                new ProductConfiguration("RAM", "8GB", 0),
                new ProductConfiguration("RAM", "16GB", 200)
        ));
    }

    public static List<ProductConfiguration> getConfigurations(String productType) {
        return configurationsByType.getOrDefault(productType, List.of());
    }
}
