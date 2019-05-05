package marketplaceserverapi.models;

import javax.persistence.*;

/**
 * Product contains product data. Each product has:
 *   String title - a unique name to differentiate between products
 *   double price - price (in dollars)
 *   int inventoryCount - a number to determine if the product is available
 *
 * Note: All methods related to the inventory count is synchronized
 * for multithreading/concurrency and prevent the race condition (so that
 * two threads cannot access/manipulate inventoryCount at the same time). Without
 * synchronization, two users may be able to buy more than the inventory amount.
 *
 * @author Jason Liu
 */

public class Product {

    @Id
    private int productId;
    private String title;
    private double price;
    @Column(name = "inventorycount")
    private int inventoryCount;

    public Product() {}

    public Product(String title, double price, int inventoryCount) {
        this.title = title;
        this.price = price;
        this.inventoryCount = inventoryCount;
    }

    public Product(int id, Product product) {
        this(product.title, product.price, product.inventoryCount);
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public synchronized int getInventoryCount() {
        return inventoryCount;
    }

    public synchronized void setInventoryCount(int inventoryCount) {
        this.inventoryCount = inventoryCount;
    }

    public synchronized boolean checkAvailable(int num)  {
        if (getInventoryCount() >= num) return true;
        return false;
    }

    public void decrementInventoryCount() {
        decrementInventoryCount(1);
    }

    public synchronized Product decrementInventoryCount(int num) throws IllegalArgumentException {
        checkAvailable(num);
        inventoryCount -= num;
        return this;
    }

    public Product update(Product product) {
        this.title = product.title;
        this.inventoryCount = product.inventoryCount;
        return product;
    }

    /**
     * Hashes the product to an integer value. This prevents conflicts when using
     * a HashMap in Cart. The prime 31 is chosen arbitrarily for the hash function.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    /**
     * Checks if the product equals the given object
     */
    @Override
    public boolean equals (Object obj) {
        if (obj instanceof Product)
            return ((Product) obj).title.equals(this.title);
        else if (obj instanceof String)
            return obj.equals(this.title);
        return false;
    }
}
