package onlineshop;

import onlineshop.exceptions.ProductOutOfStockException;
import onlineshop.model.*;
import onlineshop.service.CommandLineService;
import onlineshop.service.OrderProcessor;
import onlineshop.service.OrderPersistance;
import onlineshop.service.ProductManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandLineService commandLineService = new CommandLineService();
        commandLineService.start();
    }
}