package marketplaceserverapi.repositories;

import marketplaceserverapi.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart, Integer> {

    Cart save(Cart cart);

    boolean existsByUserId(int userId);

    List<Cart> findAll();

    void deleteByUserId(int userId);

    List<Cart> findByUserId(int userId);
}