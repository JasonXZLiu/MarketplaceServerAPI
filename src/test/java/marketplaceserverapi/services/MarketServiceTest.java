package marketplaceserverapi.services;

import marketplaceserverapi.implementations.MarketServiceImpl;
import marketplaceserverapi.models.Product;
import marketplaceserverapi.repositories.ProductRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.security.InvalidKeyException;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class MarketServiceTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void givenProductToAddToMarket_whenProductIsAddedToMarket_thenNewMarketWithProductIsReceived() throws IOException, InvalidKeyException {
        // Given
        MarketService marketService = new MarketServiceImpl(productRepository);
        Product product = new Product("Shirt", 100, 10);

        // when
        marketService.addProduct(product);

        // then
        assertTrue(marketService.getMarket().contains(product));
    }

//    @Test
//    public void givenProductToPurchaseIsNotAvailable_whenProductIsPurchased_thenNullIsReceived() throws IOException, InvalidKeyException {
//        // Given
//        MarketService marketService = new MarketServiceImpl(productRepository);
//        String title = "Basketball";
//
//        // when
//        Product product = marketService.purchaseProduct(title);
//
//        // then
//        assertNull(product);
//    }
}
