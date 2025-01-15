package onlineshop.model;

public enum SmartphoneColors {
    black(1, "Czarny"),
    white(2, "Biały"),
    blue(3, "Niebieski");

    private int option;
    private String color;

    SmartphoneColors(int option, String color) {
        this.option = option;
        this.color = color;
    }

    public int getOption() {
        return option;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
