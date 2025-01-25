package icesi.cmr.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class DeliveryCertificateDTO {

    private Integer id;

    private Long deliveryDate;

    private String notes;

    private Integer quantity;

    private String equipmentId;

    private String equipmentInventaryCode;

    private Integer contractId;

}
