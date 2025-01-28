package onlineshop.service;

import onlineshop.exceptions.OrderProcessingException;
import onlineshop.model.Order;
import onlineshop.model.Product;
import onlineshop.model.ProductConfiguration;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa ma funkcjonalnosc przetwarzania zamowien w sposob synchroniczny i asynchroniczny.
 * Obsluguje generowanie faktyr oraz zapis zamowien przy uzyciu wielowatkowosci
 */
public class OrderProcessor {
    private final OrderRepository orderRepository;

    public OrderProcessor() {
        this.orderRepository = new OrderRepository();
    }

    /**
     * Przetwarza zamowienie synchronicznie.
     * Wyswietla szczegoly zamowienia oraz generuje fakture.
     */
    public void processOrder(Order order) {
        synchronized (OrderRepository.class) {
            orderRepository.saveOrder(order);
        }
        generateInvoice(order);
        System.out.println("Zamowienie ID: " + order.getOrderId() + " przetworzone");
    }

    /**
     * Generuje fakture jako plik tekstowy
     */
    private void generateInvoice(Order order) {
        String fileName = "Faktura_" + order.getOrderId() + ".txt";
        try (var writer = new java.io.FileWriter(fileName)) {
            writer.write("Faktura dla zamówienia ID: " + order.getOrderId() + "\n");
            writer.write("Dane klienta: " + order.getCustomerName() + "\n");
            writer.write("Email: " + order.getCustomerEmail() + "\n");
            writer.write("Produkty:\n");

            order.getProducts().forEach(product -> {
                try {
                    writer.write("- " + product.getName() + ", cena brutto: " + product.getPrice() + " zł\n");
                } catch (Exception e) {
                    throw new RuntimeException("Błąd podczas zapisu produktu do faktury: " + e.getMessage());
                }
            });

            System.out.println("Faktura wygenerowana: " + fileName);
        } catch (Exception e) {
            throw new OrderProcessingException("Błąd generowania faktury: " + e.getMessage());
        }
    }
}