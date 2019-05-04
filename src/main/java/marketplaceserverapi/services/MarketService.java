package marketplaceserverapi.services;

import marketplaceserverapi.models.Product;

import java.security.InvalidKeyException;
import java.util.Collection;
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

    Product getProductById(int productId) throws InvalidKeyException;

    boolean containsProduct(Product product) throws InvalidKeyException;

    void addProduct(Product product) throws InvalidKeyException;

    Collection<Product> addProducts(Product[] products) throws InvalidKeyException;

    Collection<Product> addProducts(List<Product> products) throws InvalidKeyException;

    Product updateProduct(int productId, Product product) throws InvalidKeyException;

    List<Product> getAvailable();

    Product deleteProduct(int productId) throws InvalidKeyException;

    Collection<Product> getCollection();

    ConcurrentMap<String, Product> getAllProducts();

    Collection<Product> getAvailableCollection();
}
