package onlineshop.model;

public enum SmartphoneColors {
    black(1, "Czarny"),
    white(2, "Biały"),
    blue(3, "Niebieski");

    private int id;
    private String color;

    SmartphoneColors(int id, String color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }
}
