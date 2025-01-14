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
        this(orderId, customerName, customerEmail, new ArrayList<>(cart.getProducts()));
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

    /**
     * Wyswietla szczegóły zamówienia w konsoli.
     */
    public void displayOrderDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        System.out.println("ID zamówienia: " + orderId);
        System.out.println("Klient: " + customerName + " email: " + customerEmail);
        System.out.println("Data zamówienia: " + orderDate.format(formatter));
        System.out.println("Produkty w zamówieniu:");
        products.forEach(System.out::println);
        System.out.println("Zastosowany rabat: " + discountValue + " zł");
        System.out.println("Łączna kwota zamówienia: " + totalPrice + " zł");
    }

    /**
     * Formatuje zamówienie do zapisania w pliku.
     */
    public String formatOrder(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return String.format("Zamowienie ID: %d\n" +
                        "Klient: %s email: %s\n" +
                        "Data zamowienia: %s\n" +
                        "Produkty:\n%s" +
                        "Laczna kwota: %.2f zl\n",
                order.getOrderId(),
                order.getCustomerName(),
                order.getCustomerEmail(),
                order.getOrderDate().format(formatter),
                order.getProducts().stream()
                        .map(product -> String.format("%s, cena: %.2f zł\n", product.getName(), product.getPrice()))
                        .collect(Collectors.joining()),
                order.getTotalPrice());
    }

    public ZonedDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(ZonedDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public List<Product> getProducts() {
        return products;
    }


    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
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
}
