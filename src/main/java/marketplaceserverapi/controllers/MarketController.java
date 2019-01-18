package marketplaceserverapi.controllers;

import marketplaceserverapi.models.Product;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Collection;

/**
 * MarketController is used to handle HTTP requests.
 *
 * MarketController allows users to:
 *      get a list of products on the market
 *      view a list of available products (products that have inventory)
 *      query for a specific product
 *      purchase one product (without creating a cart).
 *
 * @author Jason Liu
 */
@RestController
@RequestMapping("/market")
public class MarketController {
    /** IoC */
    @Autowired
    @Qualifier("marketService")
    private MarketService marketService;

    public MarketController() throws IOException, InvalidKeyException {

    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Product> getProducts() {
        return marketService.getCollection();
    }

    @RequestMapping(path = "/available", method = RequestMethod.GET)
    public Collection<Product> avalableProducts() {
        return marketService.getAvailableCollection();
    }

    @RequestMapping(path = "/{title}", method = RequestMethod.GET)
    public Product getProductFromMarket(@PathVariable("title") String productTitle) throws InvalidKeyException {
        return marketService.getProductByTitle(productTitle);
    }

    @RequestMapping(path = "/purchase/{title}", method = RequestMethod.GET)
    public Product purchaseProduct(@PathVariable("title") String productTitle) throws InvalidKeyException {
        return marketService.purchaseProduct(productTitle);
    }
}
