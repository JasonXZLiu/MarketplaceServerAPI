package marketplaceserverapi.services;

import marketplaceserverapi.implementations.CartLocatorServiceImpl;
import marketplaceserverapi.model.Cart;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.Assert.assertEquals;

public class CartLocatorServiceTest {

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
