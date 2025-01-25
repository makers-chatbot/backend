package icesi.cmr.dto;

import icesi.cmr.model.relational.companies.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractDTO {

    private Integer id;

    private Long startDate;

    private Long endDate;

    private Float monthlyCost;

    private String contractNumber;

    private Boolean accepted;

    private Boolean isClosed;

    private Integer departmentId;

}
