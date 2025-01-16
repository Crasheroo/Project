package onlineshop.model;

public class Electronics extends Product{
    public Electronics(int id, String name, double price, int amountOfAvailable) {
        super(id, name, price, amountOfAvailable);
    }

    @Override
    public String toString() {
        return "Elektronika: ID: " + getId() + ", nazwa: " + getName() + ", cena: " + getPrice() + ", dostepnych sztuk: " + getItemsAvailable();
    }
}
