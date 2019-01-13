package marketplaceserverapi.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Cart contains cart data. Each cart has:
 *   String id         - a unique id (too differentiate between carts
 *   double totalPrice - in dollars
 *   HashMap<Product, Integer> items - a map of items in the cart where
 *                                     Product represents the product and
 *                                     Integer is the number of each product in the cart
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
    /**
     * HashMap requires a unique serialization method to avoid passing
     * back the product's references.
     */
    @JsonSerialize(using = ProductMapToArraySerializer.class)
    private HashMap<Product, Integer> items;
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

    public HashMap<Product, Integer> getItems() {
        return items;
    }

    public void setItems(HashMap<Product, Integer> items) {
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

    private Product getProductFromCart(String productTitle) {
        for (Map.Entry<Product, Integer> item : items.entrySet()) {
            if (item.getKey().getTitle().equals(productTitle)) return item.getKey();
        }
        return null;
    }

    private void adjustTotalPrice(double num) {
        totalPrice += num;
    }

    public Cart addToCart(Product product, int num) throws InvalidParameterException {
        if (num <= 0) throw new InvalidParameterException("Number of products to purchase must be positive: " + num);
        items.put(product, items.getOrDefault(product, 0) + num);
        adjustTotalPrice(product.getPrice() * num);
        return this;
    }

    public Cart removeFromCart(Product product, int num) {
        if (getProductFromCart(product.getTitle()) == null) return this;

        if (num <= 0) throw new InvalidParameterException("Number of products to remove must be positive: " + num);
        if (items.get(product) <= num) {
            adjustTotalPrice(-items.get(product) * product.getPrice());
            items.remove(product);
        }
        else {
            adjustTotalPrice(-num * product.getPrice());
            items.put(product, items.get(product) - num);
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
        for (Map.Entry<Product, Integer> item : items.entrySet()) {
            clone.items.put(item.getKey(), item.getValue());
        }
        clone.id = this.id;
        clone.completed = this.completed;
        return clone;
    }
}
