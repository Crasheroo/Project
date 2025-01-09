package onlineshop.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int orderId;
    private String customerName;
    private String customerEmail;
    private List<Product> products;
    private double totalPrice;
    private double discountValue;
    private LocalDateTime orderDate;

    public Order(int orderId, String customerName, String customerEmail, List<Product> products) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.products = products;
        this.discountValue = 0.0;
        this.totalPrice = calculateTotalPrice();
        this.orderDate = LocalDateTime.now();
    }

    public static Order fromCart(int orderId, String customerName, String customerEmail, Cart cart) {
        return new Order(orderId, customerName, customerEmail, new ArrayList<>(cart.getProducts()));
    }

    public double calculateTotalPrice() {
        double total = products.stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
        return Math.max(0, total - discountValue);
    }

    public void applyDiscount(Discount discount) {
        double total = products.stream()
                .mapToDouble(products -> products.getPrice())
                .sum();

        if (total >- discount.getMinimumOrderValue()) {
            double percentageDiscount = total * discount.getPercentage();
            double fixedDiscount = discount.getFixedAmount();

            this.discountValue = Math.min(total, Math.max(percentageDiscount, fixedDiscount));
            this.totalPrice = calculateTotalPrice();
            System.out.println("Zastosowano rabat: " + this.discountValue + " zł");
        }
    }

    public void displayOrderDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("ID zamówienia: " + orderId);
        System.out.println("Klient: " + customerName + " email: " + customerEmail);
        System.out.println("Data zamówienia: " + orderDate.format(formatter));
        System.out.println("Produkty w zamówieniu:");
        products.forEach(System.out::println);
        System.out.println("Zastosowany rabat: " + discountValue + " zł");
        System.out.println("Łączna kwota zamówienia: " + totalPrice + " zł");
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
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

    public void setProducts(List<Product> products) {
        this.products = products;
        this.totalPrice = calculateTotalPrice();
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
