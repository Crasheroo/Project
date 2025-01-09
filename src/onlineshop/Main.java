package onlineshop;

import onlineshop.model.Computer;
import onlineshop.model.Electronics;
import onlineshop.model.Smartphone;

public class Main {
    public static void main(String[] args) {
        Computer laptop = new Computer(1, "Laptop gejmingowy", 10_000, 69, "", 0);
        laptop.configureSpecification();

        Smartphone phone = new Smartphone(2, "Samsung", 3000, 21, "", 2137);
        phone.chooseSpecification();

        Electronics tv = new Electronics(3, "Telewizor FullHD", 5000, 1);

        tv.getInfo();
    }
}