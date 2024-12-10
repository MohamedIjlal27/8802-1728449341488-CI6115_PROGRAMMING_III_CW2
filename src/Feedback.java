public class Feedback {
    private final int rating; 
    private final String comment;
    private final String pizzaName;
    private final String username;

    public Feedback(int rating, String comment, String pizzaName, String username) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
        this.comment = comment;
        this.pizzaName = pizzaName;
        this.username = username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return username + " rated " + pizzaName + " " + rating + " stars: " + comment;
    }
} 