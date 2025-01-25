package icesi.cmr.services.interfaces;

import icesi.cmr.dto.products.ProductDTO;
import icesi.cmr.exceptions.ProductTypeRequiredException;
import icesi.cmr.model.noRelational.products.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {



    void deleteProduct(String id);

    void createProduct(ProductDTO productDTO);

    ProductDTO parseProductDTO(Map<String, Object> productData ) throws ProductTypeRequiredException;

    List<Product> getProducts();

    Product getProduct(String id);

    List<Product> getProductByCategory(String category);

    void updateProductFields(String productId, Map<String, Object> fieldsToUpdate);

}
