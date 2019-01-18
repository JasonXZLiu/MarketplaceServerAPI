package marketplaceserverapi.implementations;

import marketplaceserverapi.models.Product;
import marketplaceserverapi.repositories.ProductRepository;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * MarketServiceImpl provides an implementation of MarketService.
 * MarketServiceImpl stores a list of products offered on the market.
 *
 * This implementation uses the ProductRepository to CRUD entities in
 * memory database (H2).
 *
 * Note: In the previous version, MarketService was implemented by a static
 * ConcurrentMap as a marketplace should only have one instance of market
 * (only one list of products).
 *
 * @author Jason Liu
 */
@Service
@Qualifier("marketService")
public class MarketServiceImpl implements MarketService {
    @Autowired
    private ProductRepository productRepository;

    public MarketServiceImpl() {

    }

    public MarketServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getMarket() {
        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productList::add);
        return productList;
    }

    public Product getProductByTitle(String productId) throws InvalidKeyException {
        List<Product> products = productRepository.findByTitleIgnoreCase(productId);
        if (products == null || products.size() == 0)  return null;
        return products.get(0);
    }

    public boolean containsProduct(Product product) {
        List<Product> products = productRepository.findByTitleIgnoreCase(product.getTitle());
        return products != null && products.size() > 0;
    }

    public void addProduct(Product product) throws InvalidKeyException {
        if (containsProduct(product))  updateProduct(product.getTitle(),product);
        productRepository.save(product);
    }

    public Collection<Product> addProducts(Product[] products) throws InvalidKeyException {
        addProducts(Arrays.asList(products));
        return getMarket();
    }

    public Collection<Product> addProducts(List<Product> products) throws InvalidKeyException {
        for (Product product : products) {
            addProduct(product);
        }
        return getMarket();
    }

    public Product updateProduct(String productId, Product product) throws InvalidKeyException {
        if (getProductByTitle(productId) != null) return productRepository.save(product);
        throw new InvalidKeyException(productId + " does not yet exist on the market.");
    }

    public List<Product> getAvailable() {
        List<Product> result = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            if (product.checkAvailable(1))
                result.add(product);
        }
        return result;
    }

    public Product deleteProduct(String productId) throws InvalidKeyException {
        Product product = getProductByTitle(productId);
        if (product == null) return null;
        productRepository.delete(product);
        return product;
    }

    public List<Product> getCollection() {
        return getMarket();
    }

    public ConcurrentMap<String, Product> getAllProducts() {
        List<Product> products = getMarket();
        ConcurrentMap<String, Product> map = new ConcurrentHashMap<>();
        for (Product product : products) {
            map.put(product.getTitle(), product);
        }
        return map;
    }

    public Collection<Product> getAvailableCollection() {
        return getAvailable();
    }

    public boolean checkPurchase(HashMap<String, Integer> items) throws InvalidKeyException {
        for (Map.Entry<String, Integer> item : items.entrySet()) {
            Product product = getProductByTitle(item.getKey());
            if (product == null) return false;
            if (!product.checkAvailable(Math.max(0, item.getValue()))) return false;
        }
        return true;
    }

    public synchronized boolean finalizePurchase(HashMap<String, Integer> items) throws IllegalArgumentException, InvalidKeyException {
        if (!checkPurchase(items)) return false;
        List<Product> products = new ArrayList<>();
        for (Map.Entry<String, Integer> item : items.entrySet()) {
            Product product = getProductByTitle(item.getKey()).decrementInventoryCount(item.getValue());
            products.add(product);
        }
        productRepository.saveAll(products);
        return true;
    }

    public synchronized Product purchaseProduct(String productId) throws IllegalArgumentException, InvalidKeyException {
        HashMap<String, Integer> item = new HashMap<>();
        Product product = getProductByTitle(productId);
        if (product == null) return null;
        item.put(productId, 1);
        if (finalizePurchase(item)) return product;
        return null;
    }
}
