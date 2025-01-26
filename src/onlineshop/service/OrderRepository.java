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
        String fileName = "orders.txt";

        try (FileWriter writer = new FileWriter(fileName, true)) {
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
            System.out.println("Zamówienie zapisane w pliku: " + fileName);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu zamówienia: " + e.getMessage());
            throw new OrderProcessingException("Błąd zapisu zamówienia: " + e.getMessage());
        }
    }


    /**
     * Wczytuje wszystkie zamówienia z pliku tekstowego.
     */
    public List<String> loadOrders() {
        List<String> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orders.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Plik zamowien nie istnieje");
        } catch (IOException e) {
            System.err.println("Blad podczasa odczytu zamowien: " + e.getMessage());
        }

        return orders;
    }
}
