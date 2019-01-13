package marketplaceserverapi.implementations;

import marketplaceserverapi.model.Cart;
import marketplaceserverapi.model.Product;
import marketplaceserverapi.services.CartService;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.util.HashMap;

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

    public Cart checkOutProducts(Cart cart) {
        Cart purchased = cart.clone();
        HashMap<Product, Integer> items = cart.getItems();
        if (!marketService.finalizePurchase(items)) return cart;
        cart.clear();
        purchased.setCompleted(true);
        return purchased;
    }
}