import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private final String username;
    private final List<Pizza> favoritePizzas;
    private int loyaltyPoints;

    public UserProfile(String username) {
        this.username = username;
        this.favoritePizzas = new ArrayList<>();
        this.loyaltyPoints = 0;
    }

    public String getUsername() {
        return username;
    }

    public void addFavoritePizza(Pizza pizza) {
        favoritePizzas.add(pizza);
    }

    public List<Pizza> getFavoritePizzas() {
        return favoritePizzas;
    }

    public void addLoyaltyPoints(int points) {
        this.loyaltyPoints += points;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    @Override
    public String toString() {
        return "UserProfile [Username=" + username + ", LoyaltyPoints=" + loyaltyPoints + "]";
    }
}
