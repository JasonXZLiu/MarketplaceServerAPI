package marketplaceserverapi.implementations;

import marketplaceserverapi.models.OrderItem;
import marketplaceserverapi.models.Product;
import marketplaceserverapi.services.MarketService;
import marketplaceserverapi.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.security.InvalidKeyException;

@Service
@Qualifier("orderItemService")
public class OrderItemServiceImpl implements OrderItemService {

    /** IoC */
    @Autowired
    @Qualifier("marketService")
    private MarketService marketService;

    public OrderItemServiceImpl() {}

    public OrderItemServiceImpl(MarketService marketService) {
        this.marketService = marketService;
    }

    public OrderItem createOrderItem(int productId, int quantity) throws InvalidKeyException {
        Product product = findProductOnMarket(productId);
        return new OrderItem(productId, product.getPrice(), quantity);
    };

    public Product findProductOnMarket(int productId) throws InvalidKeyException {
        return marketService.getProductById(productId);
    }

    public void confirmOrderItemPrice(OrderItem orderItem) throws InvalidKeyException {
        Product product = findProductOnMarket(orderItem.getProductId());
        if (product.getPrice() != orderItem.getPrice())
            throw new IllegalStateException(product.getTitle() + " has updated it's price.");
    }

    public void finalizePurchase(OrderItem orderItem) throws InvalidKeyException {
        Product product = findProductOnMarket(orderItem.getProductId());
        if (product.getInventoryCount() < orderItem.getQuantity()) {
            if (product.getInventoryCount() <= 0)
                throw new IllegalStateException(product.getTitle() + " has sold out.");
            else
                throw new IllegalStateException("You are trying to purchase more than available: " + product.getTitle());
        }
    }
}
