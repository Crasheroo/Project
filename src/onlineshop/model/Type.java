package onlineshop.model;

public enum Type {
    PROCESSOR("Procesor"),
    ACCESSORY("Akcesorium"),
    BATTERY_CAPACITY("Pojemność baterii"),
    COLOR("Kolor"),
    RAM_SIZE("Wielkosc RAM");

    private String name;

    Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
