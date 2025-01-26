package onlineshop.model;

/**
 * Enum reprezentujący dostępne typy procesorów dla komputerów.
 */
public enum ProcessorType {
    INTEL_I5(1, "Intel i5", 0),
    INTEL_I7(2, "Intel i7", 300),
    AMD_RYZEN_5(3, "AMD Ryzen 5", 200),
    AMD_RYZEN_7(4, "AMD Ryzen 7", 400);

    private final int id;
    private final String name;
    private final double price;

    ProcessorType(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public static ProcessorType fromId(int id) {
        for (ProcessorType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Niepoprawny ID procesora: " + id);
    }
}
