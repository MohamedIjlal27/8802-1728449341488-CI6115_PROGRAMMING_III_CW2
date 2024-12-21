public class Order {
    private final Pizza pizza;
    private final String deliveryType; 
    private String status;
    private Feedback feedback;
    private double finalPrice;
    private Promotion.PromotionStrategy promotionStrategy;

    public Order(Pizza pizza, String deliveryType) {
        this.pizza = pizza;
        this.deliveryType = deliveryType;
        this.status = "Order Placed";
        this.finalPrice = calculateBasePrice(); 
    }

    public void setPromotionStrategy(Promotion.PromotionStrategy promotionStrategy) {
        this.promotionStrategy = promotionStrategy;
    }

    public void applyPromotion() {
        if (promotionStrategy != null) {
            this.finalPrice = promotionStrategy.applyPromotion(finalPrice);
        }
    }

    public void addFeedback(int rating, String comment) {
        this.feedback = new Feedback(rating, comment, pizza.getName(), "John Doe"); // Replace with actual username
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getStatus() {
        return status;
    }

    private double calculateBasePrice() {
        double basePrice = 10.0;  
        if (pizza.getToppings().size() > 0) {
            basePrice += pizza.getToppings().size() * 2.0;  
        }
        return basePrice;
    }

    @Override
    public String toString() {
        String baseString = "Order [Pizza=" + pizza + ", DeliveryType=" + deliveryType + ", Status=" + status;
        if (promotionStrategy != null) {
            baseString += ", Promotion=" + promotionStrategy.getClass().getSimpleName();
        }
        baseString += ", Final Price=" + String.format("%.2f", finalPrice) + "]";
        return baseString;
    }
}
