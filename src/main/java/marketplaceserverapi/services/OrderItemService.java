package marketplaceserverapi.services;

import marketplaceserverapi.models.OrderItem;
import marketplaceserverapi.models.Product;

import java.security.InvalidKeyException;

public interface OrderItemService {

    OrderItem createOrderItem(int productId, int quantity) throws InvalidKeyException;

    Product findProductOnMarket(int productId) throws InvalidKeyException;

    void confirmOrderItemPrice(OrderItem orderItem) throws InvalidKeyException;

    void finalizePurchase(OrderItem orderItem) throws InvalidKeyException;
}
