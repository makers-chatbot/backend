package icesi.cmr.model.noRelational.products;


import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
//Use the @TypeAlias annotation to specify the alias of the document in the database
@TypeAlias("laptop")
public class Laptop extends Product {

    private String processor;
    private String ram;
    private String storageType;
    private String storageCapacity;
    private String graphicsCard;
    private String operatingSystem;

}
