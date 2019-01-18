package marketplaceserverapi.services;

import marketplaceserverapi.implementations.CartLocatorServiceImpl;
import marketplaceserverapi.models.Cart;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.Assert.assertEquals;

public class CartLocatorServiceTest {

    @After
    public void deleteAddedUser() throws IOException, InvalidKeyException {
        CartLocatorService cartLocatorService = new CartLocatorServiceImpl();
        cartLocatorService.getCartByUserId("2").clear();
    }

    @Test
    public void givenNewUserNotAddedBefore_whenUserIdCartIsRetrieved_thenCartReceivedIsEmpty() throws IOException, InvalidKeyException {

        // given
        String userId = "2";
        CartLocatorService cartLocatorService = new CartLocatorServiceImpl();

        // when
        Cart cart = cartLocatorService.getCartByUserId(userId);

        // then
        assertEquals(cart.getItems().size(), 0);
    }
}
