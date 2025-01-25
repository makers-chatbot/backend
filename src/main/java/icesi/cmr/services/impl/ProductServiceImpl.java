package icesi.cmr.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import icesi.cmr.dto.products.LaptopDTO;
import icesi.cmr.dto.products.PhoneDTO;
import icesi.cmr.dto.products.PrinterDTO;
import icesi.cmr.dto.products.ProductDTO;
import icesi.cmr.exceptions.*;
import icesi.cmr.mappers.ProductMapper;
import icesi.cmr.model.noRelational.products.Product;
import icesi.cmr.model.relational.equipments.Equipment;
import icesi.cmr.repositories.equipments.EquipmentCategoryRepository;
import icesi.cmr.repositories.equipments.EquipmentRepository;
import icesi.cmr.repositories.noRelational.ProductRepository;
import icesi.cmr.services.interfaces.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    public void deleteProduct(String id) {

        //Delete the product from the relational database

        Product product =  productRepository.findById(id).orElseThrow(() -> new ProductNotFound("Product not found"));
        Equipment equipment = equipmentRepository.findByInventaryCode(id);

        if (equipment == null) {
            throw new ProductNotFound("Equipment not found");
        }

        //Delete the equipment from the no relational database
        productRepository.deleteById(id);
        //Delete the product from the relational database
        equipmentRepository.delete(equipment);

    }


    public void createProduct(ProductDTO productDTO) {

        if (productDTO.getStock() == null || productDTO.getStock() < 0) {
            throw new NotValidNegativeStock("Stock must be a positive number");
        }

        if (equipmentCategoryRepository.findByName(productDTO.getCategory()) == null) {
            throw new InvalidProductCategoryException("Category does not exist on relational db.");
        }

        //Verify price is a positive number
        if (productDTO.getPrice() < 0) {
            throw new InvalidPriceException("Price must be a positive number");
        }



        Equipment equipment = Equipment.builder().stock(productDTO.getStock()).build();

        Product product = productMapper.toEntity(productDTO);

        //Verify if ther category sent in the productDTO exists in the database


        Product productResponse = productRepository.save(product);

        //Set the product id of mongo to the equipment in order to maximize the relation between the two databases
        //and use the index optimization format.

        String inventaryCode = productResponse.getId();
        System.out.println("Inventary code: " + inventaryCode);
        equipment.setInventaryCode(inventaryCode);
        equipmentRepository.save(equipment);

    }

    public ProductDTO parseProductDTO(Map<String, Object> productData) throws ProductTypeRequiredException {

        String productType = (String) productData.get("productType");

        if (productType == null) {

            throw new ProductTypeRequiredException("Product type is required");

        }

        Class<? extends ProductDTO> dtoClass = getProductDTOClass(productType);

        var productDTO = objectMapper.convertValue(productData, dtoClass);

        System.out.println("ProductDTO  on productServiceImpl by convertValue: " + productDTO + "\n");

        return objectMapper.convertValue(productData, dtoClass);
    }

    @Override
    public List<Product> getProducts() {

        return productRepository.findByStockGreaterThan(0);

    }

    @Override
    public Product getProduct(String id) {

        Product product = productRepository.findByIdAndStockGreaterThan(id, 0);

        if ( product == null) {
            throw new ProductNotFound("Product not found or stock is 0 or less.");
        }
        return product;
    }

    @Override
    public List<Product> getProductByCategory(String category) {

        if (equipmentCategoryRepository.findByName(category) == null) {
            throw new InvalidProductCategoryException("Category does not exist on relational db.");
        }
        return productRepository.findByCategoryAndStockGreaterThan(category, 0);
    }

    @Override
    public void updateProductFields(String productId, Map<String, Object> fieldsToUpdate) {

        //verify if the product exists

        if (productRepository.findById(productId).isEmpty()) {
            throw new ProductNotFound("Product not found");
        }

        //If the stock is being updated, verify that the stock is a positive number
        if (fieldsToUpdate.containsKey("stock")) {
            Integer stock = (Integer) fieldsToUpdate.get("stock");
            if (stock < 0) {
                throw new NotValidNegativeStock("Stock must be a positive number");
            }

            //Increase the stock of the equipment in the relational database

            Equipment equipment = equipmentRepository.findByInventaryCode(productId);

            if (equipment == null) {
                throw new ProductNotFound("Equipment not found on relational db trying to modify stock.");
            }

            equipment.setStock(stock);
            equipmentRepository.save(equipment);
        }

        //If the price is being updated, verify that the price is a positive number

        if (fieldsToUpdate.containsKey("price")) {
            Float price = Float.valueOf(fieldsToUpdate.get("price").toString());
            if (price < 0) {
                throw new InvalidPriceException("Price must be a positive number");
            }
        }

        //If the category is being updated, verify that the category exists in the relational database

        if (fieldsToUpdate.containsKey("category")) {
            String category = (String) fieldsToUpdate.get("category");
            if (equipmentCategoryRepository.findByName(category) == null) {
                throw new InvalidProductCategoryException("Category does not exist on relational db.");
            }
        }

        Query query = new Query(Criteria.where("_id").is(productId));

        Update update = new Update();

        //Add the fields to update to the update object without updating the complete object
        fieldsToUpdate.forEach(update::set);

        mongoTemplate.updateFirst(query, update, Product.class);

    }

    private Class<? extends ProductDTO> getProductDTOClass(String productType) throws InvalidProductType {
        return switch (productType.toLowerCase()) {
            case "laptop" -> LaptopDTO.class;
            case "printer" -> PrinterDTO.class;
            case "phone" -> PhoneDTO.class;
            // Add cases for other product types
            default -> throw new InvalidProductType("Invalid product type.");
        };
    }

}
