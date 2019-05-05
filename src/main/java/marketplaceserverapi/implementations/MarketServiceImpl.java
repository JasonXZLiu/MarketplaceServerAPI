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

    public Product getProductById(int productId) throws InvalidKeyException {
        List<Product> products = productRepository.findByProductId(productId);
        if (products == null || products.size() == 0)  return null;
        return products.get(0);
    }

    public boolean containsProduct(Product product) throws InvalidKeyException {
        return getProductById(product.getProductId()) != null;
    }

    public int createProductId() {
        List<Product> products = (List<Product>) productRepository.findAll();
        return products.size() + 1;
    }

    public void addProduct(Product productWithoutId) throws InvalidKeyException {
        int newProductId = createProductId();
        Product product = new Product(newProductId, productWithoutId);
        if (containsProduct(product)) updateProduct(product.getProductId(), product);
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

    public Product updateProduct(int productId, Product product) throws InvalidKeyException {
        if (containsProduct(product)) return productRepository.save(product);
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

    public Product deleteProduct(int productId) throws InvalidKeyException {
        Product product = getProductById(productId);
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
}
