package marketplaceserverapi.services;

import marketplaceserverapi.implementations.MarketServiceImpl;
import marketplaceserverapi.model.Product;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class MarketServiceTest {

    @Test
    public void givenProductToAddToMarket_whenProductIsAddedToMarket_thenNewMarketWithProductIsReceived() throws IOException, InvalidKeyException {
        // Given
        Product product = new Product("Test_Shopify", 100, 10);
        MarketService marketService = new MarketServiceImpl();

        // when
        marketService.addProduct(product);

        // then
        assertTrue(marketService.getMarket().contains(product));
    }

    @Test
    public void givenProductToPurchaseIsNotAvailable_whenProductIsPurchased_thenNullIsReceived() throws IOException, InvalidKeyException {
        // Given
        String title = "Advanced_Shopify";
        MarketService marketService = new MarketServiceImpl();

        // when
        Product product = marketService.purchaseProduct(title);

        // then
        assertNull(product);
    }
}
