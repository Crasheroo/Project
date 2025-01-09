package Task1;

import Task1.Model.Computer;
import Task1.Model.Electronics;
import Task1.Model.Smartphone;

public class Main {
    public static void main(String[] args) {
        Computer laptop = new Computer(1, "Laptop gejmingowy", 10_000, 69, "dobry", 0);
        laptop.configureSpecification();

        Smartphone phone = new Smartphone(2, "Samsung", 3000, 21, "", 2137);
        phone.chooseSpecification();

        Electronics tv = new Electronics(3, "Telewizor FullHD", 5000, 1);

        System.out.println("Produkt: " + tv.getName() + ", Cena: " + tv.getPrice());
    }
}
