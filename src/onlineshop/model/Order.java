package onlineshop.model;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Klasa reprezentujaca zamówienie w sklepie internetowym.
 * Przechowując informacje o zamówieniu, takie jak ID zamówienia, dane klienta,
 * lista produktów, łączna cena, wartość rabatu oraz data zamówienia.
 */
public class Order {
    private int orderId;
    private String customerName;
    private String customerEmail;
    private List<Product> products;
    private double totalPrice;
    private double discountValue;
    private ZonedDateTime orderDate;

    public Order() {}

    public Order(int orderId, String customerName, String customerEmail, List<Product> products) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.products = products;
        this.discountValue = 0.0;
        this.totalPrice = calculateTotalPrice();
        this.orderDate = ZonedDateTime.now();
    }

    /**
     * Tworzy zamówienie na podstawie koszyka.
     */
    public Order(int orderId, String customerName, String customerEmail, Cart cart) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;

        this.products = cart.getItems().stream()
                .map(CartItem::getProduct)
                .collect(Collectors.toList());

        this.discountValue = 0.0;
        this.totalPrice = calculateTotalPrice();
        this.orderDate = ZonedDateTime.now();
    }

    /**
     * Oblicza całkowitą cenę zamówienia, uwzględniając zastosowany rabat.
     */
    public double calculateTotalPrice() {
        double total = products.stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
        return Math.max(0, total - discountValue);
    }

    /**
     * Zastosowuje rabat do zamówienia, wybierając wyzszy rabat z dwoch opcji:
     * preocentowego lub kwotowego.
     */
    public void applyDiscount(Discount discount) {
        if (this.totalPrice >= discount.getMinimumOrderValue()) {
            double percentageDiscount = this.totalPrice * discount.getPercentage();
            double fixedDiscount = discount.getFixedAmount();

            this.discountValue = Math.min(this.totalPrice, Math.max(percentageDiscount, fixedDiscount));
            this.totalPrice = calculateTotalPrice();
            System.out.println("Zastosowano rabat: " + this.discountValue + " zł");
        }
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public List<Product> getProducts() {
        return products;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order order)) return false;
        return orderId == order.orderId && Double.compare(totalPrice, order.totalPrice) == 0 && Objects.equals(customerName, order.customerName) && Objects.equals(customerEmail, order.customerEmail) && Objects.equals(products, order.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerName, customerEmail, products, totalPrice);
    }

    @Override
    public String toString() {
        String message = String.format(
                "ID zamówienia: %s\nKlient: %s, email: %s\nData zamówienia: %s\nProdukty w zamówieniu:\n",
                orderId,
                customerName,
                customerEmail,
                orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"))
        );

        String produkty = getProducts().stream()
                .map(product -> String.format("- %s", product))
                .collect(Collectors.joining("\n"));

        String rest = String.format(
                "\nZastosowany rabat: %.2f zł\nŁączna kwota zamówienia: %.2f zł",
                discountValue,
                totalPrice
        );

        return message + produkty + rest;
    }
}
