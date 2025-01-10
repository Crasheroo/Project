package onlineshop.model;

public class Discount {
    private String code;
    private double percentage;
    private double fixedAmount;
    private double minimumOrderValue;

    public Discount(String code, double percentage, double fixedAmount, double minimumOrderValue) {
        this.code = code;
        this.percentage = percentage;
        this.fixedAmount = fixedAmount;
        this.minimumOrderValue = minimumOrderValue;
    }

    public String getCode() {
        return code;
    }

    public double getPercentage() {
        return percentage;
    }

    public double getFixedAmount() {
        return fixedAmount;
    }

    public double getMinimumOrderValue() {
        return minimumOrderValue;
    }
}
