package marketplaceserverapi.services;

import marketplaceserverapi.models.Cart;
import marketplaceserverapi.models.Product;

import java.security.InvalidKeyException;

/**
 * CartService performs all actions that relate to a cart
 * and the market.
 *
 * Note: The service is created as an interface to allow
 * for scalability and future changes to its implementation.
 *
 * @author Jason Liu
 */
public interface CartService {
    Product findProductOnMarket(String productTitle) throws InvalidKeyException;

    Cart addToCart(Cart cart, String productTitle) throws InvalidKeyException;

    Cart addToCart(Cart cart, String productTitle, String num) throws InvalidKeyException;

    Cart removeFromCart(Cart cart, String productTitle) throws InvalidKeyException;

    Cart removeFromCart(Cart cart, String productTitle, String num) throws InvalidKeyException;

    Cart checkOutCart(Cart cart) throws InvalidKeyException;
}
