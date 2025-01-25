package icesi.cmr.dto;


import icesi.cmr.model.relational.companies.Company;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDTO {


    private Integer id;

    private String name;

    private String description;

    private Integer companyId;

}
