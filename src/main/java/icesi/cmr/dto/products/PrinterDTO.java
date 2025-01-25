package icesi.cmr.dto.products;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)

public class PrinterDTO extends ProductDTO {

    private String printingTechnology;
    private List<String> connectivityOptions;

}
