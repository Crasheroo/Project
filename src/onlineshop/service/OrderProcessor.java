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
    private final OrderRepository orderRepository;

    public OrderProcessor(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.orderRepository = new OrderRepository();
    }

    /**
     * Przetwarza zamowienie asynchronicznie.
     */
    public void processOrderAsync(Order order) {
        executorService.submit(() -> {
            try {
                System.out.println("Rozpoczynam przetwarzanie zamowienia");
                processOrder(order);
                orderRepository.saveOrder(order);
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
            System.out.println(order.toString());
            generateInvoice(order);
        } catch (Exception e) {
            throw new OrderProcessingException("Problem podczas robienia zamowienia: " + e.getMessage());
        }
    }

    /**
     * Generuje fakture jako plik tekstowy
     */
    private void generateInvoice(Order order) {
        String fileName = "Faktura_" + order.getOrderId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(String.format("Zamówienie ID: %d\n", order.getOrderId()));
            writer.write(String.format("Klient: %s\n", order.getCustomerName()));
            writer.write(String.format("Email: %s\n", order.getCustomerEmail()));
            writer.write(String.format("Data zamówienia: %s\n\n",
                    order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

            writer.write("Produkty:\n");
            double totalNet = 0;
            for (Product product : order.getProducts()) {
                double netPrice = product.getPrice() / 1.23;
                writer.write(String.format("- %s, cena netto: %.2f zł, cena brutto: %.2f zł\n",
                        product.getName(), netPrice, product.getPrice()));

                if (!product.getConfigurations().isEmpty()) {
                    writer.write("  Konfiguracje:\n");
                    for (var config : product.getConfigurations()) {
                        writer.write(String.format("    - %s: %s, cena: %.2f zł\n",
                                config.getType(), config.getValue(), config.getPrice()));
                    }
                }

                totalNet += netPrice;
            }

            double vat = totalNet * 0.23; // VAT 23%
            double totalGross = totalNet + vat;
            writer.write("\nPodsumowanie:\n");
            writer.write(String.format("Suma netto: %.2f zł\n", totalNet));
            writer.write(String.format("VAT (23%%): %.2f zł\n", vat));
            writer.write(String.format("Suma brutto: %.2f zł\n", totalGross));

            System.out.println("Faktura wygenerowana: " + fileName);
        } catch (IOException e) {
            System.err.println("Błąd podczas generowania faktury: " + e.getMessage());
            throw new OrderProcessingException("Błąd generowania faktury: " + e.getMessage());
        }
    }

    /**
     * Zamyka watki uzywane do przetwarzania zamowien.
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS)) {
                System.err.println("Nie udało się zakończyć wszystkich wątków w wyznaczonym czasie.");
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.err.println("Procesor został przerwany podczas oczekiwania na zakończenie wątków.");
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
        System.out.println("Procesor został zamknięty.");
    }
}