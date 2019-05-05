package marketplaceserverapi.implementations;

import marketplaceserverapi.CartAction;
import marketplaceserverapi.models.Cart;
import marketplaceserverapi.models.OrderItem;
import marketplaceserverapi.repositories.CartRepository;
import marketplaceserverapi.services.CartService;
import marketplaceserverapi.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Qualifier("cartService")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    @Qualifier("orderItemService")
    private OrderItemService orderItemService;

    public CartServiceImpl() {}

    public CartServiceImpl(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    public synchronized Cart getCartByUserId(int userId) {
        Cart cart;
        if (cartRepository.existsByUserId(userId)) {
            cart = cartRepository.findByUserId(userId).get(0);
        } else {
            cart = new Cart(userId);
        }
        removeTimedOutCart();
        return cart;
    }

    private void removeTimedOutCart() {
        LocalDateTime now = LocalDateTime.now();
        for (Cart cart : cartRepository.findAll()) {
            if (cart.getLastTouched().until(now, ChronoUnit.MINUTES) > 30)
                cartRepository.deleteByUserId(cart.getUserId());
        }
    }

    public Cart dispatchCartAction(String userIdString, CartAction action, int productId, int quantity) throws InvalidKeyException {
        int userId = Integer.parseInt(userIdString);
        Cart cart = getCartByUserId(userId);
        cartRepository.deleteByUserId(userId);
        switch (action) {
            case ADD:
                cart = addToCart(cart, productId, quantity);
                break;
            case REMOVE:
                cart = removeFromCart(cart, productId, quantity);
                break;
            case CHECKOUT:
                cart = checkOutCart(cart);
                break;
            case CLEAR:
                cart.clear();
                break;
            default:
                break;
        }
        cartRepository.save(cart);
        removeTimedOutCart();
        return cart;
    }

    public Cart updateLastTouched(Cart cart) {
        cart.setLastTouched();
        return cart;
    }

    public int findProductIndexInCart(Cart cart, int productId) {
        List<OrderItem> orderItems = cart.getOrderItems();
        for (int i = 0; i < orderItems.size(); i++) {
            if (orderItems.get(i).getProductId() == productId) return i;
        }
        return -1;
    }

    public OrderItem findProductInCart(Cart cart, int productId) {
        int idx = findProductIndexInCart(cart, productId);
        if (idx == -1) return null;
        else return cart.getOrderItems().get(idx);
    }

    public void recalculateTotal(Cart cart) {
        int total = 0;
        List<OrderItem> orderItems = cart.getOrderItems();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        cart.setTotalPrice(total);
    }

    public Cart addToCart(Cart cart, int productId, int quantity) throws InvalidParameterException, InvalidKeyException {
        if (quantity <= 0) throw new InvalidParameterException("Number of products to purchase must be positive: " + quantity);
        OrderItem orderItem = findProductInCart(cart, productId);
        if (orderItem == null) {
            orderItem = orderItemService.createOrderItem(productId, quantity);
            cart.addOrderItem(orderItem);
        } else {
            int curQuantity = orderItem.getQuantity();
            orderItem.setQuantity(curQuantity + quantity);
        }
        recalculateTotal(cart);
        return cart;
    }

    public Cart removeFromCart(Cart cart, int productId, int quantity) {
        int orderItemIdx = findProductIndexInCart(cart, productId);
        if (orderItemIdx == -1) return cart;

        if (quantity <= 0) throw new InvalidParameterException("Number of products to remove must be positive: " + quantity);
        OrderItem orderItem = cart.getOrderItems().get(orderItemIdx);
        int amountToRemove = Math.min(quantity, orderItem.getQuantity());
        int curQuantity = orderItem.getQuantity();
        int newQuantity = curQuantity - amountToRemove;
        if (newQuantity == 0) cart.removeOrderItem(orderItem);
        else orderItem.setQuantity(newQuantity);

        recalculateTotal(cart);
        return cart;
    }

    public synchronized void confirmTotalPrice(Cart cart) throws InvalidKeyException {
        List<OrderItem> orderItems = cart.getOrderItems();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            orderItemService.confirmOrderItemPrice(orderItem);
        }
    }

    public synchronized boolean finalizePurchase(Cart cart) throws InvalidKeyException {
        boolean flag = true;
        List<OrderItem> orderItems = cart.getOrderItems();
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            orderItemService.finalizePurchase(orderItem);
        }
        return flag;
    }

    public synchronized Cart checkOutCart(Cart cart) throws InvalidKeyException {
        Cart purchased = cart.clone();
        finalizePurchase(cart);
        confirmTotalPrice(cart);
        cart.clear();
        purchased.setCompleted(true);
        return purchased;
    }
}
