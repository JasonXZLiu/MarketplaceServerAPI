package marketplaceserverapi.services;

import marketplaceserverapi.model.Product;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

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

    void initialProducts() throws IOException, InvalidKeyException;

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

    Collection<Product> getAvailableCollection();

    boolean finalizePurchase(HashMap<Product, Integer> items) throws IllegalArgumentException;

    Product purchaseProduct(String stringId) throws IllegalArgumentException, InvalidKeyException;
}
