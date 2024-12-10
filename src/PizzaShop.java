import java.util.Scanner;
import java.util.InputMismatchException;

public class PizzaShop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserProfile userProfile = new UserProfile("John Doe");
        PizzaShopService service = new PizzaShopService(userProfile);

        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Welcome to the Pizza Shop ---");
            System.out.println("1. Create Custom Pizza");
            System.out.println("2. View Favorite Pizzas");
            System.out.println("3. View Loyalty Points");
            System.out.println("4. View Pizza Ratings");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> service.createCustomPizza(scanner);
                    case 2 -> service.viewFavoritePizzas();
                    case 3 -> service.viewLoyaltyPoints();
                    case 4 -> service.viewPizzaRatings();
                    case 5 -> {
                        exit = true;
                        System.out.println("Thank you for visiting the Pizza Shop!");
                    }
                    default -> System.out.println("Invalid option. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); 
            }
        }
        scanner.close();
    }
}
