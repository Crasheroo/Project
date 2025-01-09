package onlineshop.service;

import onlineshop.model.Order;
import onlineshop.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class OrderProcessor {
    public void processOrder(Order order) {
        order.displayOrderDetails();
        generateFaktura(order);
    }

    private void generateFaktura(Order order) {
        String fileName = "Faktura " + order.getOrderId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Zamówienie ID: " + order.getOrderId() + "\n");
            writer.write("Klient: " + order.getCustomerName() + " email: " + order.getCustomerEmail());
            writer.write("Data zamówienia: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Produkty:\n");

            for (Product product : order.getProducts()) {
                writer.write(product.getName() + " cena: " + product.getPrice() + " zł");
            }

            writer.write("Łączna kwota: " + order.getTotalPrice() + " zł \n");

            System.out.println("Faktura wygenerowana: " + fileName);
        } catch (IOException e) {
            System.err.println("Bląd generowania ffaktury: " + e.getMessage());
        }
    }
}
