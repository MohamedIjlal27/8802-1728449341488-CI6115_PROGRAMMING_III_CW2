public class Order {
    private final Pizza pizza;
    private final String deliveryType; 
    private String status;
    private Feedback feedback;
    private double finalPrice;
    private Promotion appliedPromotion;

    public Order(Pizza pizza, String deliveryType) {
        this.pizza = pizza;
        this.deliveryType = deliveryType;
        this.status = "Order Placed";
        this.finalPrice = calculateBasePrice(); 
    }

    public void applyPromotion(Promotion promotion) {
        if (promotion != null && promotion.isActive()) {
            this.appliedPromotion = promotion;
            this.finalPrice = finalPrice * (1 - promotion.getDiscountPercentage() / 100);
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
        if (appliedPromotion != null) {
            baseString += ", Promotion=" + appliedPromotion.getName();
        }
        baseString += ", Final Price=" + String.format("%.2f", finalPrice) + "]";
        return baseString;
    }
}
