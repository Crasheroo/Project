package onlineshop.service;

import onlineshop.exceptions.OrderProcessingException;
import onlineshop.model.Order;
import onlineshop.model.Product;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OrderProcessor {
    private final ExecutorService executorService;
    private final OrdersSaving ordersSaving;

    public OrderProcessor(int numberOfThreads) {
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
        this.ordersSaving = new OrdersSaving();
    }

    public void processOrderAsync(Order order) {
        executorService.submit(() -> {
            try {
                System.out.println("Rozpoczynam przetwarzanie zamowienia");
                Thread.sleep(2000);
                processOrder(order);
                System.out.println("Zamowienie ID: " + order.getOrderId() + " przetworzone");
            } catch (InterruptedException e) {
                System.err.println("Przetwarzanie zamowienia ID: " + order.getOrderId() + " zostalo przerwane");
            } catch (Exception e) {
                throw new OrderProcessingException("Problem podczas robienia zamówienia: " + e.getMessage());
            }
        });
    }

    public void processOrder(Order order) {
        try {
            order.displayOrderDetails();
            generateFaktura(order);
            ordersSaving.saveOrder(order);
        } catch (Exception e) {
            throw new OrderProcessingException("Problem podczas robienia zamowienia: " + e.getMessage());
        }
    }

    private void generateFaktura(Order order) {
        String fileName = "Faktura " + order.getOrderId() + ".txt";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Zamówienie ID: " + order.getOrderId() + "\n");
            writer.write("Klient: " + order.getCustomerName() + " email: " + order.getCustomerEmail());
            writer.write("Data zamówienia: " + order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Produkty:\n");

            for (Product product : order.getProducts()) {
                writer.write(product.getName() + " cena: " + product.getPrice() + " zł\n");
            }

            writer.write("Łączna kwota: " + order.getTotalPrice() + " zł \n");

            System.out.println("Faktura wygenerowana: " + fileName);
        } catch (IOException e) {
            System.err.println("Bląd generowania ffaktury: " + e.getMessage());
        }
    }

    public void shutdownThread() {
        executorService.shutdown();
        System.out.println("Procesor zostal zamkniety");
    }
}
