package icesi.cmr.repositories.noRelational;

import icesi.cmr.model.noRelational.CRM.CustomerRelationshipManagement;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CMRRepository extends MongoRepository<CustomerRelationshipManagement, String> {




    Optional<CustomerRelationshipManagement> findByCompanyIdAndDepartmentId(String companyId, String departmentId);






}
