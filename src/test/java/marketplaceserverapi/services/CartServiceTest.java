package marketplaceserverapi.services;

import marketplaceserverapi.implementations.CartServiceImpl;
import marketplaceserverapi.implementations.MarketServiceImpl;
import marketplaceserverapi.model.Cart;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartServiceTest {

    @Test
    public void givenProductTitleAndNumberToAddToCart_whenProductsAreAddedToCart_thenNewCartWithCorrectTotalPriceIsReceived() throws IOException, InvalidKeyException {
        // Given
        String title = "Shopify";
        String num = "10";
        MarketService marketService = new MarketServiceImpl();
        CartService cartService = new CartServiceImpl(marketService);
        Cart cart = new Cart("1");

        // when
        cartService.addToCart(cart, title, num);

        // then
        assertEquals(790, cart.getTotalPrice());
    }


    @Test
    public void givenProductTitleToAddToCart_whenProductIsAddedToCart_thenNewCartWithProductIsReceived() throws IOException, InvalidKeyException {
        // Given
        String title = "Shopify";
        MarketService marketService = new MarketServiceImpl();
        CartService cartService = new CartServiceImpl(marketService);
        Cart cart = new Cart("1");

        // when
        cartService.addToCart(cart, title);

        // then
        assertTrue(cart.getItems().containsKey(marketService.getProductByTitle(title)));
    }

    @Test
    public void givenCartToCheckOut_whenCartIsCheckedOut_thenNewCartWithCompletedAsTrueIsReceived() throws IOException, InvalidKeyException {
        // Given
        String title = "Shopify";
        Cart cart = new Cart("1");
        MarketService marketService = new MarketServiceImpl();
        CartService cartService = new CartServiceImpl(marketService);
        cartService.addToCart(cart, title);

        // when
        Cart newCart = cartService.checkOutProducts(cart);

        // then
        assertEquals(true, newCart.getCompleted());
    }
}
