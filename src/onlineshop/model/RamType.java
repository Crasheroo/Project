package onlineshop.model;

/**
 * Enum reprezentujący dostępne ilości RAM dla komputerów.
 */
public enum RamType {
    RAM_8GB(1, "8GB", 0),
    RAM_16GB(2, "16GB", 200),
    RAM_32GB(3, "32GB", 400);

    private final int id;
    private final String size;
    private final double price;

    RamType(int id, String size, double price) {
        this.id = id;
        this.size = size;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public static RamType fromId(int id) {
        for (RamType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Niepoprawny ID RAM: " + id);
    }
}