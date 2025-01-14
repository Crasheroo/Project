package onlineshop.service;

import onlineshop.model.Order;

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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(order.formatOrder(order));
            writer.newLine();
            System.out.println("Zamówienie zapisane w " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Blad podczas zapisu " + e.getMessage());
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
