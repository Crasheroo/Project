package onlineshop.service;

import onlineshop.exceptions.OrderProcessingException;
import onlineshop.model.Order;
import onlineshop.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Klasa ma funkcjonalnosc przetwarzania zamowien w sposob synchroniczny i asynchroniczny.
 * Obsluguje generowanie faktyr oraz zapis zamowien przy uzyciu wielowatkowosci
 */
public class OrderProcessor {
    private final ExecutorService executorService;
    private final OrderPersistance orderPersistance;

    public OrderProcessor(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.orderPersistance = new OrderPersistance();
    }

    /**
     * Przetwarza zamowienie asynchronicznie.
     */
    public void processOrderAsync(Order order) {
        executorService.submit(() -> {
            try {
                System.out.println("Rozpoczynam przetwarzanie zamowienia");
                processOrder(order);
                orderPersistance.saveOrder(order);
                System.out.println("Zamowienie ID: " + order.getOrderId() + " przetworzone");
            } catch (Exception e) {
                throw new OrderProcessingException("Problem podczas robienia zamówienia: " + e.getMessage());
            }
        });
    }

    /**
     * Przetwarza zamowienie synchronicznie.
     * Wyswietla szczegoly zamowienia oraz generuje fakture.
     */
    private void processOrder(Order order) {
        try {
            order.displayOrderDetails();
            generateInvoice(order);
        } catch (Exception e) {
            throw new OrderProcessingException("Problem podczas robienia zamowienia: " + e.getMessage());
        }
    }

    /**
     * Generuje fakture jako plik tekstowy
     */
    private void generateInvoice(Order order) {
        String fileName = "Faktura " + order.getOrderId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Zamówienie ID: " + order.getOrderId() + "\n");
            writer.write("Klient: " + order.getCustomerName() + " email: " + order.getCustomerEmail() + "\n");
            writer.write("Data zamówienia: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Produkty:\n");

            for (Product product : order.getProducts()) {
                writer.write(product.getName() + " cena: " + product.getPrice() + " zł\n");
            }

            writer.write("Łączna kwota: " + order.getTotalPrice() + " zł \n");

            System.out.println("Faktura wygenerowana: " + fileName);
        } catch (IOException e) {
            throw new OrderProcessingException("Bląd generowania ffaktury: " + e.getMessage());
        }
    }

    /**
     * Zamyka watki uzywane do przetwarzania zamowien.
     */
    public void shutdown() {
        executorService.shutdown();
        System.out.println("Procesor zostal zamkniety");
    }
}
