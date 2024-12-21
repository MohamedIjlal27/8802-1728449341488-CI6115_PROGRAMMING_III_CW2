import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private final String username;
    private final List<Pizza> favoritePizzas;
    private int loyaltyPoints;
    private LoyaltyState loyaltyState;

    public UserProfile(String username) {
        this.username = username;
        this.favoritePizzas = new ArrayList<>();
        this.loyaltyPoints = 0;
        this.loyaltyState = new BronzeState(this);
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
        loyaltyState.checkState();
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyState(LoyaltyState state) {
        this.loyaltyState = state;
    }

    @Override
    public String toString() {
        return "UserProfile [Username=" + username + ", LoyaltyPoints=" + loyaltyPoints + ", LoyaltyState=" + loyaltyState.getClass().getSimpleName() + "]";
    }

    interface LoyaltyState {
        void checkState();
    }

    class BronzeState implements LoyaltyState {
        private final UserProfile userProfile;

        public BronzeState(UserProfile userProfile) {
            this.userProfile = userProfile;
        }

        @Override
        public void checkState() {
            if (userProfile.getLoyaltyPoints() >= 100) {
                userProfile.setLoyaltyState(new SilverState(userProfile));
            }
        }
    }

    class SilverState implements LoyaltyState {
        private final UserProfile userProfile;

        public SilverState(UserProfile userProfile) {
            this.userProfile = userProfile;
        }

        @Override
        public void checkState() {
            if (userProfile.getLoyaltyPoints() >= 200) {
                userProfile.setLoyaltyState(new GoldState(userProfile));
            } else if (userProfile.getLoyaltyPoints() < 100) {
                userProfile.setLoyaltyState(new BronzeState(userProfile));
            }
        }
    }

    class GoldState implements LoyaltyState {
        private final UserProfile userProfile;

        public GoldState(UserProfile userProfile) {
            this.userProfile = userProfile;
        }

        @Override
        public void checkState() {
            if (userProfile.getLoyaltyPoints() < 200) {
                userProfile.setLoyaltyState(new SilverState(userProfile));
            }
        }
    }
}
