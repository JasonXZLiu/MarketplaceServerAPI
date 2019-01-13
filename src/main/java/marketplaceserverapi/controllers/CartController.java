package marketplaceserverapi.controllers;

import marketplaceserverapi.model.Cart;
import marketplaceserverapi.services.CartLocatorService;
import marketplaceserverapi.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;

/**
 * CartController is used to handle HTTP requests. CartController
 * is autowired (dependency injection) with an instance of CartService
 * to provide/execute actions and a CartLocatorService to determine
 * each user's respective instance of Cart.
 *
 * CartController allows users to
 *      get their cart
 *      add products to their cart
 *      remove products from their cart
 *      clear the cart
 *      check out all products from their cart.
 *
 * @author Jason Liu
 */
@RestController
@RequestMapping("/cart")
public class CartController {
    /** IoC */
    @Autowired
    @Qualifier("cartService")
    public CartService cartService;

    /** IoC */
    @Autowired
    @Qualifier("cartLocatorService")
    public CartLocatorService cartLocatorService;

    public CartController() throws IOException, InvalidKeyException { }

    private Cart getCartByUserId(String userId) throws IOException, InvalidKeyException {
        return cartLocatorService.getCartByUserId(userId);
    }

    @RequestMapping(value = "/u/{userId}/add/{title}", method = RequestMethod.GET)
    public Cart addToCart(@PathVariable("userId") String userId, @PathVariable("title") String productTitle) throws InvalidKeyException, IOException {
        return cartService.addToCart(getCartByUserId(userId), productTitle);
    }

    @RequestMapping(value = "/u/{userId}/add/{title}/{num}", method = RequestMethod.GET)
    public Cart addToCart(@PathVariable("userId") String userId, @PathVariable("title") String productTitle, @PathVariable("num") String num) throws InvalidKeyException, IOException {
        return cartService.addToCart(getCartByUserId(userId), productTitle, num);
    }

    @RequestMapping(value = "/u/{userId}/remove/{title}", method = RequestMethod.DELETE)
    public Cart removeFromCart(@PathVariable("userId") String userId, @PathVariable("title") String productTitle) throws InvalidKeyException, IOException {
        return cartService.removeFromCart(getCartByUserId(userId), productTitle);
    }

    @RequestMapping(value = "/u/{userId}/remove/{title}/{num}", method = RequestMethod.DELETE)
    public Cart removeFromCart(@PathVariable("userId") String userId, @PathVariable("title") String productTitle, @PathVariable("num") String num) throws InvalidKeyException, IOException {
        return cartService.removeFromCart(getCartByUserId(userId), productTitle, num);
    }

    @RequestMapping(value = "/u/{userId}/view", method = RequestMethod.GET)
    public Cart getCart(@PathVariable("userId") String userId) throws IOException, InvalidKeyException {
        return getCartByUserId(userId);
    }

    @RequestMapping(value = "/u/{userId}/clear", method = RequestMethod.DELETE)
    public Cart clearCart(@PathVariable("userId") String userId) throws IOException, InvalidKeyException {
        return getCartByUserId(userId).clear();
    }

    @RequestMapping(path = "/u/{userId}/checkout", method = RequestMethod.GET)
    public Cart checkOutProducts(@PathVariable("userId") String userId) throws InvalidParameterException, IOException, InvalidKeyException {
        return cartService.checkOutProducts(getCartByUserId(userId));
    }
}
