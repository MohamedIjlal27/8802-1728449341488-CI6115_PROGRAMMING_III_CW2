public class Promotion {
    private final String name;
    private final double discountPercentage;
    private final boolean isActive;
    private final String season;

    // Strategy Interface
    public interface PromotionStrategy {
        double applyPromotion(double price);
    }

    // Concrete Strategy
    public class PercentageDiscountPromotion implements PromotionStrategy {
        @Override
        public double applyPromotion(double price) {
            return price * (1 - discountPercentage / 100);
        }
    }

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

    public PromotionStrategy getPromotionStrategy() {
        
        return new PercentageDiscountPromotion(); 
    }

    @Override
    public String toString() {
        return name + " - " + discountPercentage + "% off" + (isActive ? " (Active)" : " (Inactive)");
    }
} 