package icesi.cmr.model.noRelational.CRM;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "crm")
public class CustomerRelationshipManagement {


    @Indexed(unique = true)
    private String companyId;
    @Indexed(unique = true)
    private String departmentId;

    private List<Interaction> interactions;

}
