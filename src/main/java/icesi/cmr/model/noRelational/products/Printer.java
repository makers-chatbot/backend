package icesi.cmr.model.noRelational.products;

import lombok.*;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Document(collection = "products")
@TypeAlias("printer")
public class Printer extends Product {

    private String printingTechnology;
    private List<String> connectivityOptions;

}
