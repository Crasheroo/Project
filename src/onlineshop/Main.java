package onlineshop;

import onlineshop.service.CommandLineService;

public class Main {
    public static void main(String[] args) {
        CommandLineService commandLineService = new CommandLineService();
        commandLineService.start();
    }
}