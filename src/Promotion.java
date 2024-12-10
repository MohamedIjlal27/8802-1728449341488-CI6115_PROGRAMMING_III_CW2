public class Promotion {
    private final String name;
    private final double discountPercentage;
    private final boolean isActive;
    private final String season; 

    public Promotion(String name, double discountPercentage, boolean isActive, String season) {
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.isActive = isActive;
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getSeason() {
        return season;
    }

    @Override
    public String toString() {
        return name + " - " + discountPercentage + "% off" + (isActive ? " (Active)" : " (Inactive)");
    }
} 