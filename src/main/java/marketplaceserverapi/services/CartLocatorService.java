package marketplaceserverapi.services;

import marketplaceserverapi.models.Cart;

import java.io.IOException;
import java.security.InvalidKeyException;

/**
 * CartLocatorService is a service to find a user's cart given
 * their userId.
 *
 * Note: This service allows for concurrency (as every user has
 * their own cart).
 *
 * Also note: The service is created as an interface to allow
 * for scalability and future changes to its implementation.
 *
 * @author Jason Liu
 */
public interface CartLocatorService {
    Cart getCartByUserId (String userId) throws IOException, InvalidKeyException;
}
