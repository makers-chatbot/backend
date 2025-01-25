package icesi.cmr.services.interfaces;

import icesi.cmr.dto.InteractionRequestDTO;
import icesi.cmr.model.noRelational.CRM.CustomerRelationshipManagement;

import java.util.List;
import java.util.Optional;

public interface InteractionService {



    void createInteaction(InteractionRequestDTO interactionRequestDTO);

    List<CustomerRelationshipManagement> getInteractionsByCompany();

    Optional<CustomerRelationshipManagement> getInteractionsByCompanyAndDepartment(String companyId, String departmentId);


}
