package icesi.cmr.dto.products;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PhoneDTO extends ProductDTO {

    private String screenSize;
    private String batteryLife;
    private String cameraResolution;
    private String operatingSystem;

}
