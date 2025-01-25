package icesi.cmr.controllers;

import icesi.cmr.dto.ProductRequestDTO;
import icesi.cmr.dto.products.ProductDTO;
import icesi.cmr.exceptions.*;
import icesi.cmr.services.impl.ProductServiceImpl;
import icesi.cmr.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@RequestMapping("api/products")
public class ProductController {

    @Autowired
    private ProductService productServiceImpl;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody Map<String, Object> productData) {

        try {
            ProductDTO productDTO = productServiceImpl.parseProductDTO(productData);

            productServiceImpl.createProduct(productDTO);

            return ResponseEntity.ok().body("Product created successfully");

        }catch (InvalidPriceException | InvalidProductType | ProductTypeRequiredException | NotValidNegativeStock e ){
            e.printStackTrace();

            return ResponseEntity.badRequest().body(e.getMessage());

        }catch (HttpClientErrorException.Unauthorized e){
            e.printStackTrace();

            return ResponseEntity.status(401).body(e.getMessage());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    /**
     * This method is used to delete a product from the database
     * @param id the id of the product that is stored in the no relational database.
     * @return a response entity with a message indicating if the product was deleted successfully or not
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productServiceImpl.deleteProduct(id);
            return ResponseEntity.ok().body("Product deleted successfully");
        } catch (ProductNotFound e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable String id) {
        try {
            return ResponseEntity.ok().body(productServiceImpl.getProduct(id));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getProducts(@RequestParam(required = false) String category) {
        try {

            if (category == null) {
                return ResponseEntity.ok().body(productServiceImpl.getProducts());
            }else {
                return ResponseEntity.ok().body(productServiceImpl.getProductByCategory(category));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProductFields(@PathVariable String id, @RequestBody Map<String, Object> fieldsToUpdate) {
        try {
            productServiceImpl.updateProductFields(id, fieldsToUpdate);
            return ResponseEntity.ok().body("Product updated successfully");
        } catch (ProductNotFound e){
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }catch (InvalidPriceException | InvalidProductCategoryException  | NotValidNegativeStock e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

}
