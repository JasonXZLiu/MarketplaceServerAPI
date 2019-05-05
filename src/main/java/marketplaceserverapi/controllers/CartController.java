package marketplaceserverapi.controllers;

import marketplaceserverapi.CartAction;
import marketplaceserverapi.models.Cart;
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

    private final CartAction VIEW = CartAction.VIEW;
    private final CartAction ADD = CartAction.ADD;
    private final CartAction REMOVE = CartAction.REMOVE;
    private final CartAction CLEAR = CartAction.CLEAR;
    private final CartAction CHECKOUT = CartAction.CHECKOUT;

    /** IoC */
    @Autowired
    @Qualifier("cartService")
    public CartService cartService;

    public CartController() throws IOException, InvalidKeyException { }

    @RequestMapping(value = "/u/{userId}/add/{id}", method = RequestMethod.GET)
    public Cart addToCart(@PathVariable("userId") String userId, @PathVariable("id") String productId) throws InvalidKeyException, IOException {
        return cartService.dispatchCartAction(userId, ADD, Integer.parseInt(productId), 1);
    }

    @RequestMapping(value = "/u/{userId}/add/{id}/{quantity}", method = RequestMethod.GET)
    public Cart addToCart(@PathVariable("userId") String userId, @PathVariable("id") String productId, @PathVariable("quantity") String quantity) throws InvalidKeyException, IOException {
        return cartService.dispatchCartAction(userId, ADD, Integer.parseInt(productId), Integer.parseInt(quantity));
    }

    @RequestMapping(value = "/u/{userId}/remove/{id}", method = RequestMethod.DELETE)
    public Cart removeFromCart(@PathVariable("userId") String userId, @PathVariable("id") String productId) throws InvalidKeyException, IOException {
        return cartService.dispatchCartAction(userId, REMOVE, Integer.parseInt(productId), 1);
    }

    @RequestMapping(value = "/u/{userId}/remove/{id}/{quantity}", method = RequestMethod.DELETE)
    public Cart removeFromCart(@PathVariable("userId") String userId, @PathVariable("id") String productId, @PathVariable("quantity") String quantity) throws InvalidKeyException, IOException {
        return cartService.dispatchCartAction(userId, REMOVE, Integer.parseInt(productId), Integer.parseInt(quantity));
    }

    @RequestMapping(value = "/u/{userId}/view", method = RequestMethod.GET)
    public Cart getCart(@PathVariable("userId") String userId) throws IOException, InvalidKeyException {
        return cartService.dispatchCartAction(userId, VIEW, 0,0);
    }

    @RequestMapping(value = "/u/{userId}/clear", method = RequestMethod.DELETE)
    public Cart clearCart(@PathVariable("userId") String userId) throws IOException, InvalidKeyException {
        return cartService.dispatchCartAction(userId, CLEAR, 0, 0);
    }

    @RequestMapping(path = "/u/{userId}/checkout", method = RequestMethod.GET)
    public Cart checkOutProducts(@PathVariable("userId") String userId) throws InvalidParameterException, IOException, InvalidKeyException {
        return cartService.dispatchCartAction(userId, CHECKOUT, 0, 0);
    }
}
