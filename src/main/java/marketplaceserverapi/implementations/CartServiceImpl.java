package marketplaceserverapi.implementations;

import marketplaceserverapi.models.Cart;
import marketplaceserverapi.models.Product;
import marketplaceserverapi.services.CartService;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

/**
 * CartServiceImpl provides an implementation of CartService.
 * CartServiceImpl contains an instance to marketService
 * in order to retrieve/get products by title from the market.
 *
 * @author Jason Liu
 */
@Service
@Component("cartService")
public final class CartServiceImpl implements CartService {
    /** IoC */
    @Autowired
    @Qualifier("marketService")
    private MarketService marketService;

    public CartServiceImpl(MarketService marketService) {
        this.marketService = marketService;
    }

    public Product findProductOnMarket(String productTitle) throws InvalidKeyException {
        return marketService.getProductByTitle(productTitle);
    }

    public Cart addToCart(Cart cart, String productTitle) throws InvalidKeyException {
        return cart.addToCart(findProductOnMarket(productTitle), 1);
    }

    public Cart addToCart(Cart cart, String productTitle, String num) throws InvalidKeyException {
        return cart.addToCart(findProductOnMarket(productTitle), Integer.parseInt(num));
    }

    public Cart removeFromCart(Cart cart, String productTitle) throws InvalidKeyException {
        return cart.removeFromCart(findProductOnMarket(productTitle), 1);
    }

    public Cart removeFromCart(Cart cart, String productTitle, String num) throws InvalidKeyException {
        return cart.removeFromCart(findProductOnMarket(productTitle), Integer.parseInt(num));
    }

    public void confirmTotalPrice(Cart cart) {
        double totalPrice = 0;
        ConcurrentMap<String, Product> productMap = marketService.getAllProducts();
        for (Map.Entry<String, Integer> item : cart.getItems().entrySet()) {
            Product product = productMap.get(item.getKey());
            if (product == null) throw new IllegalStateException(item.getKey() + " does not exist on the market anymore.");
            totalPrice += item.getValue() * product.getPrice();
        }
        if (cart.getTotalPrice() != totalPrice) throw new IllegalStateException("A product's price was updated while you tried to purchase. Please re-add the products to your cart.");
    }

    public synchronized Cart checkOutCart(Cart cart) throws InvalidKeyException {
        Cart purchased = cart.clone();
        HashMap<String, Integer> items = cart.getItems();
        confirmTotalPrice(cart);
        if (!marketService.finalizePurchase(items)) return cart;
        cart.clear();
        purchased.setCompleted(true);
        return purchased;
    }
}