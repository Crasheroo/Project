package onlineshop.model;

import java.util.List;
import java.util.Objects;

public class Order {
    private int orderId;
    private String customerName;
    private String customerEmail;
    private List<Product> products;
    private double totalPrice;

    public Order(int orderId, String customerName, String customerEmail, List<Product> products) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.products = products;
    }

    public double calculateTotalPrice() {
        return products.stream()
                .mapToDouble(product -> product.getPrice())
                .sum();
    }

    public void displayOrderDetails() {
        System.out.println("ID zamówienia: " + orderId);
        System.out.println("Klient: " + customerName + " email: " + customerEmail);
        System.out.println("Produkty w zamówieniu:");
        products.forEach(product -> product.getInfo());
        System.out.println("Łączna kwota zamówienia: " + totalPrice + " zł");
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
