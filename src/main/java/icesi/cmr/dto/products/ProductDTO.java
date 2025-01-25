package icesi.cmr.dto.products;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {

    private String productType; // "laptop", "printer", "phone", etc.

    private String name;
    private String brand;
    private String model;
    private String description;
    private Float price;
    private Integer stock;
    private Integer warrantyPeriod;
    private Long releaseDate;
    private Map<String, Object> specifications;
    private Map<String, Object> images;
    private String category;
}
