package icesi.cmr.model.noRelational.products;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {

    @Id
    //Use the @Indexed annotation to specify that the field is indexed to improve query performance
    @Indexed(unique = true)
    private String id;
    private String name;
    private String brand;
    private String model;
    private String description;
    private Float price;
    private Integer stock;
    private Integer warrantyPeriod; // in months
    private Long releaseDate;
    private Map<String, Object> specifications;
    private Map<String, Object> images;
    private String category;
}
