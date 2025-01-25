package icesi.cmr.services.impl;

import com.mongodb.client.result.UpdateResult;
import icesi.cmr.dto.InteractionRequestDTO;
import icesi.cmr.exceptions.EntityNotFound;
import icesi.cmr.model.noRelational.CRM.CustomerRelationshipManagement;
import icesi.cmr.model.noRelational.CRM.Interaction;
import icesi.cmr.repositories.companies.CompanyRepository;
import icesi.cmr.repositories.companies.DepartmentRepository;
import icesi.cmr.repositories.equipments.EquipmentCategoryRepository;
import icesi.cmr.repositories.noRelational.CMRRepository;
import icesi.cmr.services.interfaces.InteractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Autowired
    private CMRRepository cmrRepository;

    @Autowired
    private EquipmentCategoryRepository equipmentCategoryRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public void createInteaction(InteractionRequestDTO interactionRequestDTO) {

        //Verify if the category already exists in the relational database

        if (equipmentCategoryRepository.findByName(interactionRequestDTO.getCategory()) == null) {
            throw new EntityNotFound("Category not found when creating interaction");
        }


        //Verify if the company and department exists in the relational database

        if (companyRepository.findById(Integer.valueOf(interactionRequestDTO.getCompanyId())).isEmpty()) {
            throw new EntityNotFound("Company not found when creating interaction");
        }

        if (departmentRepository.findById(Integer.valueOf(interactionRequestDTO.getDepartmentId())).isEmpty()) {
            throw new EntityNotFound("Department not found when creating interaction");
        }

        //Create query to find the interaction or create it if it doesn't exist

        Query query = new Query();
        query.addCriteria(Criteria.where("companyId").is(interactionRequestDTO.getCompanyId())
                .and("departmentId").is(interactionRequestDTO.getDepartmentId())
                .and("interactions.category").is(interactionRequestDTO.getCategory()));

        // Build the update object
        Update update = new Update();
        if (interactionRequestDTO.getLikes() != null) {
            update.inc("interactions.$.likes", interactionRequestDTO.getLikes());
        }
        if (interactionRequestDTO.getInteractionsCounter() != null) {
            update.inc("interactions.$.interactionsCounter", interactionRequestDTO.getInteractionsCounter());
        }

        // Perform the update
        UpdateResult result = mongoTemplate.updateFirst(query, update, CustomerRelationshipManagement.class);

        if (result.getMatchedCount() == 0) {
            // No matching interaction found; add it to the CRM document
            addOrUpdateInteraction(interactionRequestDTO);
        }
    }

    private void addOrUpdateInteraction(InteractionRequestDTO dto) {
        // Build the query to find the CRM document
        Query crmQuery = new Query();
        crmQuery.addCriteria(Criteria.where("companyId").is(dto.getCompanyId())
                .and("departmentId").is(dto.getDepartmentId()));

        // Create the new interaction
        Interaction newInteraction = new Interaction();
        newInteraction.setCategory(dto.getCategory());
        newInteraction.setLikes(dto.getLikes() != null ? dto.getLikes() : 0);
        newInteraction.setInteractionsCounter(dto.getInteractionsCounter() != null ? dto.getInteractionsCounter() : 0);

        // Build the update to push the new interaction
        Update crmUpdate = new Update().push("interactions", newInteraction);

        // Use upsert to create the CRM document if it doesn't exist
        mongoTemplate.upsert(crmQuery, crmUpdate, CustomerRelationshipManagement.class);
    }

    @Override
    public List<CustomerRelationshipManagement> getInteractionsByCompany() {
        return cmrRepository.findAll();
    }

    @Override
    public Optional<CustomerRelationshipManagement> getInteractionsByCompanyAndDepartment(String companyId, String departmentId) {
        return cmrRepository.findByCompanyIdAndDepartmentId(companyId, departmentId);
    }


}
