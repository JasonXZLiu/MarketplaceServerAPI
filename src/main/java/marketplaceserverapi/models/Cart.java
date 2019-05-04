package marketplaceserverapi.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart contains cart data. Each cart has:
 *   String id         - a unique id (too differentiate between carts
 *   double totalPrice - in dollars
 *   HashMap<String, Integer> items - a map of items in the cart where
 *                                    String represents the product's title and
 *                                    Integer is the number of each product in the cart
 *   boolean completed - a flag to keep track of which carts have already been purchased
 *   LocalDateTime lastTouched - a time to determine abandoned carts
 *
 * Cart has the ability to retrieve a list of the items in the cart,
 * add or remove products, and clear the cart.
 *
 * @author Jason Liu
 */
public class Cart {
    private String id;
    private double totalPrice;
    private List<OrderItem> orderItems;
    private boolean completed;
    private LocalDateTime lastTouched;

    public Cart() {
        orderItems = new ArrayList<>();
        totalPrice = 0;
        completed = false;
        lastTouched = LocalDateTime.now();
    }

    public Cart(String id) {
        this();
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean getCompleted() {return completed;}

    public void setCompleted(boolean completed) {this.completed = completed;}

    public LocalDateTime getLastTouched() {
        return lastTouched;
    }

    public void setLastTouched() {
        this.lastTouched = LocalDateTime.now();
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
    }

    public Cart clear() {
        totalPrice = 0;
        orderItems = new ArrayList<>();
        return this;
    }

    public Cart clone() {
        Cart clone = new Cart();
        clone.totalPrice = this.totalPrice;
        for (int i = 0; i < orderItems.size(); i++) {
            clone.orderItems.add(this.orderItems.get(i));
        }
        clone.id = this.id;
        clone.completed = this.completed;
        return clone;
    }
}
