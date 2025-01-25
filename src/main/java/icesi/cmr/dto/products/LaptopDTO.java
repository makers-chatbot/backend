package icesi.cmr.dto.products;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LaptopDTO extends ProductDTO {

    private String processor;
    private String ram;
    private String storageType;
    private String storageCapacity;
    private String graphicsCard;
    private String operatingSystem;

}
