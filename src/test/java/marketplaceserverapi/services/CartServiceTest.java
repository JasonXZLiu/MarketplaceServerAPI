package marketplaceserverapi.services;

import marketplaceserverapi.implementations.CartLocatorServiceImpl;
import marketplaceserverapi.repositories.ProductRepository;
import org.junit.After;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.security.InvalidKeyException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class CartServiceTest {
    @Autowired
    private ProductRepository productRepository;

    @After
    public void deleteAddedUser() throws IOException, InvalidKeyException {
        CartLocatorService cartLocatorService = new CartLocatorServiceImpl();
        cartLocatorService.getCartByUserId("11").clear();
    }

//    @Test
//    public void givenProductTitleAndNumberToAddToCart_whenProductsAreAddedToCart_thenNewCartWithCorrectTotalPriceIsReceived() throws IOException, InvalidKeyException {
//        // Given
//        MarketService marketService = new MarketServiceImpl(productRepository);
//        String title = "Ball";
//        String num = "10";
//        CartService cartService = new CartServiceImpl(marketService);
//        Cart cart = new Cart("11");
//
//        // when
//        cartService.addToCart(cart, title, num);
//
//        // then
//        assertEquals(250, cart.getTotalPrice());
//    }
//
//
//    @Test
//    public void givenProductTitleToAddToCart_whenProductIsAddedToCart_thenNewCartWithProductIsReceived() throws IOException, InvalidKeyException {
//        // Given
//        MarketService marketService = new MarketServiceImpl(productRepository);
//        String title = "Ball";
//        CartService cartService = new CartServiceImpl(marketService);
//        Cart cart = new Cart("11");
//
//        // when
//        cartService.addToCart(cart, title);
//
//        // then
//        assertTrue(cart.getItems().containsKey(title));
//    }
//
//    @Test
//    public void givenCartToCheckOut_whenCartIsCheckedOut_thenNewCartWithCompletedAsTrueIsReceived() throws IOException, InvalidKeyException {
//        // Given
//        MarketService marketService = new MarketServiceImpl(productRepository);
//        String title = "Ball";
//        Cart cart = new Cart("11");
//        CartService cartService = new CartServiceImpl(marketService);
//        cartService.addToCart(cart, title);
//
//        // when
//        Cart newCart = cartService.checkOutCart(cart);
//
//        // then
//        assertEquals(true, newCart.getCompleted());
//    }
}
