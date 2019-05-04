package marketplaceserverapi.services;

import marketplaceserverapi.models.Cart;
import marketplaceserverapi.models.OrderItem;

import java.security.InvalidKeyException;

public interface CartService {

    Cart updateLastTouched(Cart cart);

    int findProductIndexInCart(Cart cart, int productId);

    OrderItem findProductInCart(Cart cart, int productId);

    void recalculateTotal(Cart cart);

    Cart addToCart(Cart cart, int productId, int num) throws InvalidKeyException;

    Cart removeFromCart(Cart cart, int productId, int num);

    void confirmTotalPrice(Cart cart) throws InvalidKeyException;

    Cart checkOutCart(Cart cart) throws InvalidKeyException;
}
