package onlineshop.service;

import onlineshop.exceptions.OrderProcessingException;
import onlineshop.model.Order;
import onlineshop.model.Product;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Klasa zapewnia funkcje zapisywania i odczytu zamówień
 * w pliku tekstowym. Jest do twałego przechowywania danych zamowien.
 */
public class OrderRepository {
    private static final String FILE_NAME = "orders.txt";

    /**
     * Zapisuje zamówienie do pliku tekstowego w sposób zsychnronizowany.
     */
    public synchronized void saveOrder(Order order) {
        synchronized (OrderRepository.class) {
            try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
                writer.write(String.format("Zamówienie ID: %d\n", order.getOrderId()));
                writer.write(String.format("Klient: %s\n", order.getCustomerName()));
                writer.write(String.format("Email: %s\n", order.getCustomerEmail()));
                writer.write(String.format("Data zamówienia: %s\n\n",
                        order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

                writer.write("Produkty:\n");
                for (Product product : order.getProducts()) {
                    writer.write(String.format("- %s, cena brutto: %.2f zł\n", product.getName(), product.getPrice()));

                    if (!product.getConfigurations().isEmpty()) {
                        writer.write("  Konfiguracje:\n");
                        for (var config : product.getConfigurations()) {
                            writer.write(String.format("    - %s: %s, cena: %.2f zł\n",
                                    config.getType(), config.getValue(), config.getPrice()));
                        }
                    }
                }

                writer.write("\n===============================\n");
                System.out.println("Zamówienie zapisane w pliku: " + FILE_NAME);
            } catch (IOException e) {
                System.err.println("Błąd podczas zapisu zamówienia: " + e.getMessage());
                throw new OrderProcessingException("Błąd zapisu zamówienia: " + e.getMessage());
            }
        }
    }
}
