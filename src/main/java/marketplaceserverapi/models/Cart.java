package marketplaceserverapi.models;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
    private HashMap<String, Integer> items;
    private boolean completed;
    private LocalDateTime lastTouched;

    public Cart() {
        items = new HashMap<>();
        totalPrice = 0;
        completed = false;
        lastTouched = LocalDateTime.now();
    }

    public Cart(String id) {
        this();
        this.id = id;
    }

    public HashMap<String, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<String, Integer> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean getCompleted() {return completed;}

    public void setCompleted(boolean completed) {this.completed = completed;}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getLastTouched() {
        return lastTouched;
    }

    public void setLastTouched(LocalDateTime lastTouched) {
        this.lastTouched = lastTouched;
    }

    public Cart updateLastTouched() {
        this.lastTouched = LocalDateTime.now();
        return this;
    }

    private String getProductTitleFromCart(String productTitle) {
        for (Map.Entry<String, Integer> item : items.entrySet()) {
            if (item.getKey().equalsIgnoreCase(productTitle)) return item.getKey();
        }
        return null;
    }

    private void adjustTotalPrice(double num) {
        totalPrice += num;
    }

    public Cart addToCart(Product product, int num) throws InvalidParameterException {
        if (num <= 0) throw new InvalidParameterException("Number of products to purchase must be positive: " + num);
        items.put(product.getTitle(), items.getOrDefault(product.getTitle(), 0) + num);
        adjustTotalPrice(product.getPrice() * num);
        return this;
    }

    public Cart removeFromCart(Product product, int num) {
        if (getProductTitleFromCart(product.getTitle()) == null) return this;

        if (num <= 0) throw new InvalidParameterException("Number of products to remove must be positive: " + num);
        if (items.get(product.getTitle()) <= num) {
            adjustTotalPrice(-items.get(product.getTitle()) * product.getPrice());
            items.remove(product.getTitle());
        }
        else {
            adjustTotalPrice(-num * product.getPrice());
            items.put(product.getTitle(), items.get(product.getTitle()) - num);
        }
        return this;
    }

    public Cart clear() {
        totalPrice = 0;
        items = new HashMap<>();
        return this;
    }

    public Cart clone() {
        Cart clone = new Cart();
        clone.totalPrice = this.totalPrice;
        for (Map.Entry<String, Integer> item : items.entrySet()) {
            clone.items.put(item.getKey(), item.getValue());
        }
        clone.id = this.id;
        clone.completed = this.completed;
        return clone;
    }
}
