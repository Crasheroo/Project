package onlineshop.model;

import onlineshop.exceptions.ProductOutOfStockException;

import java.util.List;

public class CartItem {
    private List<ProductConfiguration> configurations;
    private Product product;
    private int quantity;

    public CartItem(Product product, List<ProductConfiguration> configurations, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.configurations = configurations;

        if (quantity > product.getStock()) {
            throw new ProductOutOfStockException(product.getName() + " nie jest dostepny");
        }
        product.decrementStock(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public List<ProductConfiguration> getConfigurations() {
        return configurations;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        int stockAdjust = quantity - this.quantity;
        if (stockAdjust > 0 && !product.isInStock(stockAdjust)) {
            throw new IllegalArgumentException("Niewystarczajaca ilosc produktu w magazynie");
        }
        product.decrementStock(stockAdjust);
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder()
                .append(String.format("Produkt: %s\n", product.getName()))
                .append(String.format("Cena podstawowa: %.2f zł\n", product.getPrice()))
                .append(String.format("Ilość: %d\n", quantity))
                .append("Wybrane konfiguracje:\n");

        configurations.forEach(config ->
                sb.append(String.format("  - %s: %s (+ %.2f zł)\n",
                        config.getType(), config.getValue(), config.getPrice())));

        double totalItemPrice = (product.getPrice() +
                configurations.stream().mapToDouble(ProductConfiguration::getPrice).sum()) * quantity;
        sb.append(String.format("Łączna cena: %.2f zł\n", totalItemPrice));

        return sb.toString();
    }
}
