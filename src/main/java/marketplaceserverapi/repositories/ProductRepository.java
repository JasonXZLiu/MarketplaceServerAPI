package marketplaceserverapi.repositories;

import marketplaceserverapi.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * ProductRepository is used to extract data from the database.
 * It extends the CrudRepository (which allows for the basic
 * CRUD functions) and adds a new method to find a product
 * given it's title (without case sensitivity).
 *
 * @author Jason Liu
 */

public interface ProductRepository extends MongoRepository<Product, Integer> {

    List<Product> findByProductId(int productId);

}
