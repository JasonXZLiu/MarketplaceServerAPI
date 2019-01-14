package marketplaceserverapi.controllers;

import marketplaceserverapi.implementations.CartLocatorServiceImpl;
import marketplaceserverapi.services.CartLocatorService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.Assert.assertEquals;

public class CartControllerTest {

    @After
    public void deletedAddedUser() throws IOException, InvalidKeyException {
        CartLocatorService cartLocatorService = new CartLocatorServiceImpl();
        cartLocatorService.getCartByUserId("1").clear();
    }

    @Test
    public void givenProductToAddToCart_whenProductTitleAndNumberToPurchaseIsRetrieved_thenNewCartIsReceived() throws IOException {

        // given
        String userId = "1";
        String title = "Shopify";
        String num = "10";
        HttpUriRequest request = new HttpGet( "http://localhost:8080/cart/u/" + userId + "/add/" + title + "/" + num);

        // when
        HttpResponse httpResponse = HttpClientBuilder.create().build().execute(request);

        // then
        assertEquals(200, httpResponse.getStatusLine().getStatusCode());
    }
}
