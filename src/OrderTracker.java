import java.util.ArrayList;
import java.util.List;

public class OrderTracker {
    private final Order order;
    private final List<String> statusHistory;

    public OrderTracker(Order order) {
        this.order = order;
        this.statusHistory = new ArrayList<>();
        this.statusHistory.add(order.getStatus());
    }

    public void startTracking() {
        new Thread(() -> {
            try {
                updateStatus("Preparing");
                Thread.sleep(1000); 
                updateStatus("Packing");
                Thread.sleep(1000); 

                updateStatus("On the way");
                Thread.sleep(1000); 

                updateStatus("Rider reached, please collect it");
                System.out.println("Press Enter to continue...");
            } catch (InterruptedException e) {
                System.out.println("Order tracking interrupted.");
            }
        }).start();
    }

    public void updateStatus(String newStatus) {
        order.updateStatus(newStatus);
        statusHistory.add(newStatus);
        notifyUser(newStatus);
    }

    private void notifyUser(String status) {
        System.out.println("Notification: Your order status is now '" + status + "'.");
    }

    public List<String> getStatusHistory() {
        return statusHistory;
    }
} 