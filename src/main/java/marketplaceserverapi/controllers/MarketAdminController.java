package marketplaceserverapi.controllers;

import marketplaceserverapi.models.Product;
import marketplaceserverapi.services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.Collection;

/**
 * MarketAdminController is used to handle HTTP requests.
 *
 * MarketAdminController allows vendors to:
 *      add products to the market
 *      remove products from the market
 *      update products on the market.
 *
 * Note: MarketAdminController is separate from MarketController
 * as only vendors should have the access. In the future,
 * vendors would be required to have authentication to change
 * their store offerings.
 *
 * @author Jason Liu
 */
@RestController
@RequestMapping("/admin/market")
public class MarketAdminController {
    /** IoC */
    @Autowired
    @Qualifier("marketService")
    private MarketService marketService;

    public MarketAdminController() throws IOException, InvalidKeyException {

    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public Collection<Product> addProducts(@RequestBody Product[] product) throws InvalidKeyException {
        return marketService.addProducts(product);
    }

    @RequestMapping(path = "/update/{productId}", method = RequestMethod.POST)
    public Product updateProduct(@PathVariable("productId") String productId, @RequestBody Product product) throws InvalidKeyException {
        return marketService.updateProduct(Integer.parseInt(productId), product);
    }

    @RequestMapping(path = "/delete/{productId}", method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable("productId") String productId) throws InvalidKeyException {
        marketService.deleteProduct(Integer.parseInt(productId));
    }
}
