package marketplaceserverapi.implementations;

import marketplaceserverapi.model.Cart;
import marketplaceserverapi.services.CartLocatorService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * CartLocatorServiceImpl provides an implementation of CartLocatorService.
 *
 * Note: CartLocatorServiceImpl is designed based off the singleton design
 * pattern (restricting the instantiation of CartLocatorService to one).
 * This is because a marketplace should only have one master list of each
 * user and their cart. In the future, a new implementation can be created
 * with a connection to a database.
 *
 * Also note: If a user abandons their cart for more than 30 minutes,
 * their cart is removed from the HashMap through the removeTimedOutCart() method.
 *
 * @author Jason Liu
 */
@Service
@Qualifier("cartLocatorService")
public final class CartLocatorServiceImpl implements CartLocatorService {
    private static HashMap<String, Cart> userCarts;

    public CartLocatorServiceImpl() throws IOException {
        if(userCarts == null)
            userCarts = new HashMap<>();
    }

    public synchronized Cart getCartByUserId(String userId) {
        removeTimedOutCart();
        for (Map.Entry<String, Cart> entry : userCarts.entrySet()) {
            if (entry.getKey().equals(userId)) return entry.getValue().updateLastTouched();
        }
        Cart cart = new Cart(userId);
        userCarts.put(userId, cart);
        return cart;
    }

    public void removeTimedOutCart() {
        Queue<String> toRemove = new LinkedList<>();
        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<String, Cart> entry : userCarts.entrySet()) {
            if (entry.getValue().getLastTouched().until(now, ChronoUnit.MINUTES) > 1)
                toRemove.add(entry.getKey());
        }
        while (!toRemove.isEmpty()) {
            userCarts.remove(toRemove.poll());
        }
    }

    /**
     * Attempt to give users (who do not have a unique id) an AnonymousAuthenticationToken
     * to maintain a user's cart and allow for multiple threads.
     */
//    public Cart getCartByUser() throws IOException, InvalidKeyException {
//        String uId = handleAuthentication();
//
//        for (Map.Entry<String, Cart> cart : userCarts.entrySet()) {
//            if (cart.getKey().equals(uId)) return cart.getValue();
//        }
//        Cart cart = new Cart();
//        userCarts.put(uId, cart);
//        return cart;
//    }
//
//    public String handleAuthentication() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null) {
//            if (!(authentication instanceof AnonymousAuthenticationToken)) {
//                UUID uuid = UUID.randomUUID();
//                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//                Authentication auth = new UsernamePasswordAuthenticationToken(uuid.toString(), uuid.toString(), authorities);
//                SecurityContextHolder.getContext().setAuthentication(auth);
//                System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//            }
//        }
//    }
}
