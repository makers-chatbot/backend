package icesi.cmr.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InteractionRequestDTO {

    private String companyId;

    private String departmentId;

    private String category;

    private Integer likes;

    private Integer interactionsCounter;

}
