package marketplaceserverapi.implementations;

import marketplaceserverapi.model.InitialMarketData;
import marketplaceserverapi.model.Product;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * MarketServiceImpl provides an implementation of MarketService.
 * MarketServiceImpl stores a list of products offered on the market.
 *
 * Note: MarketService has a static ConcurrentMap as a marketplace
 * should only have one instance of market (only one list of products).
 *
 * Also note: In the future, this implementation will be replaced with
 * a database.
 *
 * @author Jason Liu
 */
@Service
@Qualifier("marketService")
public class MarketServiceImpl implements MarketService {
    private static ConcurrentMap<String, Product> market;

    public MarketServiceImpl() throws IOException, InvalidKeyException {
        initialProducts();
    }

    public void initialProducts() throws IOException, InvalidKeyException {
        if (market == null) {
            market = new ConcurrentHashMap<>();
            addProducts(new InitialMarketData().getProducts());
        }
    }

    public Collection<Product> getMarket() {
        return market.values();
    }

    public Product getProductByTitle(String stringId) throws InvalidKeyException {
        if (market.containsKey(stringId)) return market.get(stringId);
        throw new InvalidKeyException(stringId + " does not exist on the market");
    }

    public boolean containsProduct(Product product) {
        return market.containsKey(product.getTitle());
    }

    public void addProduct(Product product) throws InvalidKeyException {
        if (containsProduct(product)) throw new InvalidKeyException(product.getTitle() + " already exists on the market.");
        else market.put(product.getTitle(), product);
    }

    public Collection<Product> addProducts(Product[] products) throws InvalidKeyException {
        for (Product product : products) {
            addProduct(product);
        }
        return market.values();
    }

    public Collection<Product> addProducts(List<Product> products) throws InvalidKeyException {
        for (Product product : products) {
            addProduct(product);
        }
        return market.values();
    }

    public Product updateProduct(String stringId, Product product) throws InvalidKeyException {
        return getProductByTitle(stringId).update(product);
    }

    public List<Product> getAvailable() {
        List<Product> res = new ArrayList<Product>();
        for (Map.Entry<String, Product> map : market.entrySet()) {
            if (map.getValue().checkAvailable(0))
                res.add(map.getValue());
        }
        return res;
    }

    public Product deleteProduct(String stringId) throws InvalidKeyException {
        Product product = getProductByTitle(stringId);
        market.remove(product);
        return product;
    }

    public Collection<Product> getCollection() {
        return getMarket();
    }

    public Collection<Product> getAvailableCollection() {
        return getAvailable();
    }

    public boolean checkPurchase(HashMap<Product, Integer> items) {
        for (Map.Entry<Product, Integer> item : items.entrySet()) {
            if (!item.getKey().checkAvailable(Math.max(0, item.getValue())))
                return false;
                // throw new IllegalArgumentException(item.getKey().getTitle() + " is sold out");
        }
        return true;
    }

    public synchronized boolean finalizePurchase(HashMap<Product, Integer> items) throws IllegalArgumentException {
        if (!checkPurchase(items)) return false;
        for (Map.Entry<Product, Integer> item : items.entrySet()) {
            item.getKey().decrementInventoryCount(item.getValue());
        }
        return true;
    }

    public synchronized Product purchaseProduct(String stringId) throws IllegalArgumentException, InvalidKeyException {
        HashMap<Product, Integer> item = new HashMap<>();
        Product product = getProductByTitle(stringId);
        item.put(product, 1);
        if (finalizePurchase(item)) return product;
        return null;
    }
}
