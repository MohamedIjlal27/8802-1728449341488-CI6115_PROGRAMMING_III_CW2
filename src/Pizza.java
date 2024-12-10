import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private final String crust;
    private final String sauce;
    private final List<String> toppings;
    private final String cheese;
    private final String name;

    private Pizza(Builder builder) {
        this.crust = builder.crust;
        this.sauce = builder.sauce;
        this.toppings = builder.toppings;
        this.cheese = builder.cheese;
        this.name = builder.name;
    }

    public static class Builder {
        private String crust;
        private String sauce;
        private List<String> toppings = new ArrayList<>();
        private String cheese;
        private String name;

        public Builder setCrust(String crust) {
            this.crust = crust;
            return this;
        }

        public Builder setSauce(String sauce) {
            this.sauce = sauce;
            return this;
        }

        public Builder addTopping(String topping) {
            this.toppings.add(topping);
            return this;
        }

        public Builder setCheese(String cheese) {
            this.cheese = cheese;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Pizza build() {
            if (name == null || crust == null || sauce == null || cheese == null) {
                throw new IllegalStateException("Missing required fields to build Pizza");
            }
            return new Pizza(this);
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getToppings() {
        return new ArrayList<>(toppings);  
    }

    @Override
    public String toString() {
        return "Pizza [Name=" + name + ", Crust=" + crust + ", Sauce=" + sauce + ", Toppings=" + toppings +
                ", Cheese=" + cheese + "]";
    }
}
