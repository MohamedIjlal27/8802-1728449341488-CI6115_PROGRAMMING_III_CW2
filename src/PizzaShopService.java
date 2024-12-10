import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

public class PizzaShopService {
    private final UserProfile userProfile;
    private List<Promotion> activePromotions = new ArrayList<>();
    private List<Feedback> allFeedback = new ArrayList<>();

    public PizzaShopService(UserProfile userProfile) {
        this.userProfile = userProfile;
        initializePromotions();
    }

    public void createCustomPizza(Scanner scanner) {
        try {
            System.out.print("Enter Pizza Name: ");
            String name = scanner.next();
            scanner.nextLine();


            String crust = null;
            System.out.println("\nChoose Crust:");
            System.out.println("1. Thin");
            System.out.println("2. Thick");
            System.out.print("Enter your choice: ");
            int crustChoice = scanner.nextInt();
            scanner.nextLine();
            switch (crustChoice) {
                case 1 -> crust = "Thin";
                case 2 -> crust = "Thick";
                default -> {
                    System.out.println("Invalid choice, defaulting to Thin.");
                    crust = "Thin";
                }
            }

            String sauce = null;
            System.out.println("\nChoose Sauce:");
            System.out.println("1. Tomato");
            System.out.println("2. Pesto");
            System.out.print("Enter your choice: ");
            int sauceChoice = scanner.nextInt();
            scanner.nextLine();
            switch (sauceChoice) {
                case 1 -> sauce = "Tomato";
                case 2 -> sauce = "Pesto";
                default -> {
                    System.out.println("Invalid choice, defaulting to Tomato.");
                    sauce = "Tomato";
                }
            }


            String cheese = null;
            System.out.println("\nChoose Cheese:");
            System.out.println("1. Mozzarella");
            System.out.println("2. Cheddar");
            System.out.print("Enter your choice: ");
            int cheeseChoice = scanner.nextInt();
            scanner.nextLine();
            switch (cheeseChoice) {
                case 1 -> cheese = "Mozzarella";
                case 2 -> cheese = "Cheddar";
                default -> {
                    System.out.println("Invalid choice, defaulting to Mozzarella.");
                    cheese = "Mozzarella";
                }
            }


            Pizza.Builder builder = new Pizza.Builder()
                    .setName(name)
                    .setCrust(crust)
                    .setSauce(sauce)
                    .setCheese(cheese);


            System.out.print("\nHow many toppings would you like to add? ");
            int toppingCount = scanner.nextInt();
            scanner.nextLine(); 
            for (int i = 0; i < toppingCount; i++) {
                System.out.print("Enter topping " + (i + 1) + ": ");
                String topping = scanner.nextLine();
                builder.addTopping(topping);
            }

            Pizza customPizza = builder.build();
            userProfile.addFavoritePizza(customPizza);

            System.out.println("\nCustom Pizza Created: " + customPizza);
            
      
            System.out.println("\nWould you like to place an order for this pizza?");
            System.out.println("1. Place Order");
            System.out.println("2. Return to Menu");
            System.out.print("Enter your choice: ");
            int orderChoice = scanner.nextInt();
            
            if (orderChoice == 1) {
                placeOrderForPizza(scanner, customPizza);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter the correct type of data.");
            scanner.nextLine();
        }
    }

    private void placeOrderForPizza(Scanner scanner, Pizza pizza) {
        
        String paymentMethod = null;
        System.out.println("\nChoose Payment Method:");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. Card");
        System.out.print("Enter your choice: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        switch (paymentChoice) {
            case 1:
                paymentMethod = "Cash on Delivery";
                System.out.println("Please keep the cash ready before the rider reaches.");
                break;
            case 2:
                paymentMethod = "Card";
                boolean validCard = false;
                while (!validCard) {
                    System.out.print("Enter card number (12 digits): ");
                    String cardNumber = scanner.nextLine();
                    if (cardNumber.matches("\\d{12}")) {
                        System.out.print("Enter card holder name: ");
                        String cardHolder = scanner.nextLine();
                        if (cardHolder.trim().isEmpty()) {
                            System.out.println("Card holder name cannot be empty.");
                            continue;
                        }
                        
                        System.out.print("Enter expiry date (MM/YY): ");
                        String expiryDate = scanner.nextLine();
                        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
                            System.out.println("Invalid expiry date format. Please use MM/YY");
                            continue;
                        }
                        
                        System.out.print("Enter CVV (3 digits): ");
                        String cvv = scanner.nextLine();
                        
                        if (cvv.matches("\\d{3}")) {
                            System.out.println("Card payment processed successfully!");
                            validCard = true;
                        } else {
                            System.out.println("Invalid CVV. Please try again.");
                        }
                    } else {
                        System.out.println("Invalid card number. Please enter 12 digits.");
                    }
                }
                break;
            default:
                System.out.println("Invalid choice, defaulting to Cash on Delivery.");
                paymentMethod = "Cash on Delivery";
        }

       
        String deliveryType = null;
        System.out.println("\nChoose Delivery Type:");
        System.out.println("1. Pickup");
        System.out.println("2. Delivery");
        System.out.print("Enter your choice: ");
        int deliveryChoice = scanner.nextInt();

        switch (deliveryChoice) {
            case 1:
                deliveryType = "Pickup";
                break;
            case 2:
                deliveryType = "Delivery";
                break;
            default:
                System.out.println("Invalid choice, defaulting to Pickup.");
                deliveryType = "Pickup";
        }

        Order order = new Order(pizza, deliveryType);
        
        showActivePromotions();
        applyPromotions(order, scanner);

        OrderTracker orderTracker = new OrderTracker(order);
        userProfile.addLoyaltyPoints(10);

        if ("Delivery".equalsIgnoreCase(deliveryType)) {
            orderTracker.startTracking();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                System.out.println("Waiting interrupted.");
            }
        } else {
            orderTracker.updateStatus("Preparing");
            orderTracker.updateStatus("Ready for Pickup");
        }

        System.out.println("Order Placed: " + order);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("You earned 10 loyalty points!");
        
        System.out.println("\nCurrent Order Status: " + order.getStatus());
        System.out.println("Press Enter to continue...");
        scanner.nextLine(); 
        scanner.nextLine(); 
        collectFeedback(scanner, order);
    }

    public void viewFavoritePizzas() {
        List<Pizza> favoritePizzas = userProfile.getFavoritePizzas();
        if (favoritePizzas.isEmpty()) {
            System.out.println("No favorite pizzas saved yet.");
        } else {
            System.out.println("\n--- Favorite Pizzas ---");
            favoritePizzas.forEach(System.out::println); 
        }
    }

    public void placeOrder(Scanner scanner) {
        System.out.println("\n--- Place Order ---");
        System.out.print("Enter Pizza Name to Order: ");
        String pizzaName = scanner.next();

        Pizza selectedPizza = userProfile.getFavoritePizzas().stream()
                .filter(pizza -> pizza.getName().equalsIgnoreCase(pizzaName))
                .findFirst()
                .orElse(null);

        if (selectedPizza != null) {
            String deliveryType = null;
            System.out.println("\nChoose Delivery Type:");
            System.out.println("1. Pickup");
            System.out.println("2. Delivery");
            System.out.print("Enter your choice: ");
            int deliveryChoice = scanner.nextInt();

            switch (deliveryChoice) {
                case 1:
                    deliveryType = "Pickup";
                    break;
                case 2:
                    deliveryType = "Delivery";
                    break;
                default:
                    System.out.println("Invalid choice, defaulting to Pickup.");
                    deliveryType = "Pickup";
            }

            Order order = new Order(selectedPizza, deliveryType);
            OrderTracker orderTracker = new OrderTracker(order);
            userProfile.addLoyaltyPoints(10);

            if ("Delivery".equalsIgnoreCase(deliveryType)) {
                orderTracker.startTracking();
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    System.out.println("Waiting interrupted.");
                }
            } else {
                orderTracker.updateStatus("Preparing");
                orderTracker.updateStatus("Ready for Pickup");
            }

            System.out.println("Order Placed: " + order);
            System.out.println("You earned 10 loyalty points!");
            
            System.out.println("\nCurrent Order Status: " + order.getStatus());
            System.out.println("Press Enter to continue...");
            scanner.nextLine();
            scanner.nextLine(); 
        } else {
            System.out.println("Pizza not found in favorites.");
        }
    }

    public void viewLoyaltyPoints() {
        System.out.println("Your Loyalty Points: " + userProfile.getLoyaltyPoints());
    }


    private void initializePromotions() {
        activePromotions.add(new Promotion("Summer Special", 20.0, true, "SUMMER"));
        activePromotions.add(new Promotion("Winter Warmup", 15.0, false, "WINTER"));
    }

    private void showActivePromotions() {
        System.out.println("\n--- Active Promotions ---");
        activePromotions.stream()
                .filter(Promotion::isActive)
                .forEach(System.out::println);
    }

    private void collectFeedback(Scanner scanner, Order order) {
        System.out.println("\nWould you like to rate your order? (1: Yes, 2: No)");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        if (choice == 1) {
            System.out.print("Rate from 1-5 stars: ");
            int rating = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Add a comment (optional): ");
            String comment = scanner.nextLine();

            order.addFeedback(rating, comment);
            allFeedback.add(order.getFeedback());
            System.out.println("Thank you for your feedback!");
        }
    }

    private void applyPromotions(Order order, Scanner scanner) {
        System.out.println("\nAvailable Promotions:");
        List<Promotion> availablePromotions = activePromotions.stream()
                .filter(Promotion::isActive)
                .toList();

        if (availablePromotions.isEmpty()) {
            System.out.println("No active promotions at this time.");
            return;
        }

        for (int i = 0; i < availablePromotions.size(); i++) {
            System.out.println((i + 1) + ". " + availablePromotions.get(i));
        }

        System.out.print("Select a promotion (0 for none): ");
        int choice = scanner.nextInt();
        if (choice > 0 && choice <= availablePromotions.size()) {
            order.applyPromotion(availablePromotions.get(choice - 1));
            System.out.println("Promotion applied successfully!");
        }
    }


    public void viewPizzaRatings() {
        System.out.println("\n--- Pizza Ratings ---");
        if (allFeedback.isEmpty()) {
            System.out.println("No ratings yet.");
            return;
        }


        Map<String, Double> averageRatings = allFeedback.stream()
                .collect(Collectors.groupingBy(
                    Feedback::getPizzaName,
                    Collectors.averagingDouble(Feedback::getRating)
                ));

        averageRatings.forEach((pizza, rating) -> 
            System.out.printf("%s: %.1f stars\n", pizza, rating));
    }
}