package marketplaceserverapi.services;

import marketplaceserverapi.models.Product;

import java.security.InvalidKeyException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

/**
 * MarketService performs all actions that relate to the
 * market.
 *
 * Note: The service is created as an interface to allow
 * for scalability and future changes to its implementation.
 *
 * @author Jason Liu
 */
public interface MarketService {

    Collection<Product> getMarket();

    Product getProductByTitle(String stringId) throws InvalidKeyException;

    boolean containsProduct(Product product);

    void addProduct(Product product) throws InvalidKeyException;

    Collection<Product> addProducts(Product[] products) throws InvalidKeyException;

    Collection<Product> addProducts(List<Product> products) throws InvalidKeyException;

    Product updateProduct(String stringId, Product product) throws InvalidKeyException;

    List<Product> getAvailable();

    Product deleteProduct(String stringId) throws InvalidKeyException;

    Collection<Product> getCollection();

    ConcurrentMap<String, Product> getAllProducts();

    Collection<Product> getAvailableCollection();

    boolean finalizePurchase(HashMap<String, Integer> items) throws IllegalArgumentException, InvalidKeyException;

    Product purchaseProduct(String stringId) throws IllegalArgumentException, InvalidKeyException;
}
