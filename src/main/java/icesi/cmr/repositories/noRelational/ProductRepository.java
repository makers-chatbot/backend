package icesi.cmr.repositories.noRelational;

import icesi.cmr.model.noRelational.products.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String > {


    List<Product> findByCategoryAndStockGreaterThan(String category, Integer stock);

    List<Product> findByStockGreaterThan(Integer stock);

    Product findByIdAndStockGreaterThan(String id, Integer stock);



}
