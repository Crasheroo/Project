package onlineshop.service;

import onlineshop.model.Order;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderPersistance {
    private static final String FILE_NAME = "orders.txt";

    public synchronized void saveOrder(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(formatOrder(order));
            writer.newLine();
            System.out.println("Zamówienie zapisane w " + FILE_NAME);
        } catch (IOException e) {
            System.err.println("Blad podczas zapisu " + e.getMessage());
        }
    }

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

    private String formatOrder(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        StringBuilder sb = new StringBuilder();
        sb.append("Zamowienie ID: ").append(order.getOrderId()).append("\n");
        sb.append("Klient: ").append(order.getCustomerName()).append(" email: ").append(order.getCustomerEmail()).append("\n");
        sb.append("Data zamowienia: ").append(order.getOrderDate().format(formatter)).append("\n");
        sb.append("Produkty:\n");
        order.getProducts()
                        .forEach(product ->
                                sb.append(product.getName()).append(", cena: ").append(product.getPrice()).append(" zł\n"));
        sb.append("Łączna kwota: ").append(order.getTotalPrice()).append(" zł\n");
        return sb.toString();
    }
}
